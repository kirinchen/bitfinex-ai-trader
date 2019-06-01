package net.surfm.crypto.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("prototype")
@Service
public class AuthClient {

	@Value("${seckey}")
	private String seckey;
	@Value("${pubkey}")
	private String pubkey;
	
	private HttpClient client;

	AuthClient init(HttpClient hc) {
		client = hc;
		
		return this;
	}
	
	// <editor-fold defaultstate="collapsed" desc="Utils">
	/**
	 * Get message signature.
	 *
	 * @param address The destination address.
	 * @param nonce   The request nonce.
	 * @param body    The request body.
	 *
	 * @return The message signature.
	 */
	protected String getAuthenticationSignature(URI address, String nonce, JSONObject body) {
		StringBuilder message = new StringBuilder("/api").append(address.getPath()).append(nonce);

		if (body.length() > 0) {
			message.append(body);
		}

		try {
			Mac hmac = Mac.getInstance("HmacSHA384");
			Charset charset = StandardCharsets.US_ASCII;

			hmac.init(new SecretKeySpec(this.seckey.getBytes(charset), "HmacSHA384"));

			return Hex.encodeHexString(hmac.doFinal(message.toString().getBytes(charset)));
		} catch (NoSuchAlgorithmException | InvalidKeyException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Get authentication headers.
	 *
	 * @param address The destination address.
	 * @param body    The request body.
	 * @param charset The request charset.
	 *
	 * @return An unmodifiable collection of HTTP headers.
	 */
	protected Collection<Header> getAuthenticationHeaders(URI address, JSONObject body, Charset charset) {
		String nonce = Long.toString(Instant.now().toEpochMilli());

		Collection<Header> headers = new ArrayList<>();
		String signature = this.getAuthenticationSignature(address, nonce, body);

		headers.add(new BasicHeader("bfx-nonce", nonce));
		headers.add(new BasicHeader("bfx-apikey", this.pubkey));
		headers.add(new BasicHeader("bfx-signature", signature));

		return Collections.unmodifiableCollection(headers);
	}
	// </editor-fold>

	/**
	 * Send an API POST request.
	 *
	 * @param endpoint The destination endpoint.
	 *
	 * @return The response body.
	 */
	protected String post(String endpoint) {
		return this.post(endpoint, Collections.<NameValuePair>emptyList());
	}

	/**
	 * Send an API POST request.
	 *
	 * @param endpoint   The destination endpoint.
	 * @param parameters The request parameters.
	 *
	 * @return The response body.
	 */
	protected String post(String endpoint, Collection<NameValuePair> parameters) {
		return this.post(endpoint, parameters, new JSONObject());
	}

	/**
	 * Send an API POST request.
	 *
	 * @param endpoint The destination endpoint.
	 * @param body     The request body.
	 *
	 * @return The response body.
	 */
	protected String post(String endpoint, JSONObject body) {
		return this.post(endpoint, Collections.<NameValuePair>emptyList(), body, StandardCharsets.UTF_8);
	}

	/**
	 * Send an API POST request.
	 *
	 * @param endpoint   The destination endpoint.
	 * @param parameters The request parameters.
	 * @param body       The request body.
	 *
	 * @return The response body.
	 */
	protected String post(String endpoint, Collection<NameValuePair> parameters, JSONObject body) {
		return this.post(endpoint, parameters, body, StandardCharsets.UTF_8);
	}

	/**
	 * Send an API POST request.
	 *
	 * @param endpoint The destination endpoint.
	 * @param body     The request body.
	 * @param charset  The request charset.
	 *
	 * @return The response body.
	 */
	protected String post(String endpoint, JSONObject body, Charset charset) {
		return this.post(endpoint, Collections.<NameValuePair>emptyList(), body, charset);
	}

	/**
	 * Send an API POST request.
	 *
	 * @param endpoint   The destination endpoint.
	 * @param parameters The request parameters.
	 * @param body       The request body.
	 * @param charset    The request charset.
	 *
	 * @return The response body.
	 */
	protected String post(String endpoint, Collection<NameValuePair> parameters, JSONObject body, Charset charset) {
		try {
			ContentType ctype = ContentType.APPLICATION_JSON.withCharset(charset);

			URI address = new URIBuilder().setScheme("https").setHost("api.bitfinex.com").setPath(endpoint).build();

			RequestBuilder request = RequestBuilder.post(address).setCharset(charset).addHeader(HttpHeaders.ACCEPT,
					ctype.getMimeType());

			if (endpoint.toLowerCase().startsWith("/v2/auth/")) {
				this.getAuthenticationHeaders(address, body, charset).forEach((header) -> {
					request.addHeader(header);
				});
			}

			if (body.length() == 0) {
				request.setEntity(new ByteArrayEntity(new byte[0]));
			} else {
				request.setEntity(new StringEntity(body.toString(), ctype));
			}

			if (!parameters.isEmpty()) {
				request.addParameters(parameters.toArray(new NameValuePair[parameters.size()]));
			}

			return EntityUtils.toString(this.client.execute(request.build()).getEntity());
		} catch (URISyntaxException | IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}
