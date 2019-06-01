/**
 * For instructions on how to build and run this example please see the original
 * gist https://gist.github.com/davide-scola/b693cb8569d05d621e6b37d26538eabd
 */
package com.bitfinex.api.rest.v2;

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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Example {

// <editor-fold defaultstate="collapsed" desc="Properties">
    /**
     * The secret key.
     */
    protected String seckey;

    /**
     * The public key.
     */
    protected String pubkey;

    /**
     * The HTTP client.
     */
    protected HttpClient client;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default contructor.
     *
     * @param client The HTTP client.
     *
     * Get secret and public keys from environment variables.
     */
    public Example(HttpClient client) {
        this(
                client,
                System.getenv("BFX_SECKEY"),
                System.getenv("BFX_PUBKEY")
        );
    }

    /**
     * Constructor.
     *
     * @param client The HTTP client.
     * @param seckey The secret key.
     * @param pubkey The public key.
     */
    public Example(HttpClient client, String seckey, String pubkey) {
        if (client == null) {
            throw new IllegalArgumentException("client");
        }

        if (seckey == null || seckey.isEmpty()) {
            throw new IllegalArgumentException("seckey");
        }

        if (pubkey == null || pubkey.isEmpty()) {
            throw new IllegalArgumentException("pubkey");
        }

        this.client = client;
        this.seckey = seckey;
        this.pubkey = pubkey;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Utils">
    /**
     * Get message signature.
     *
     * @param address The destination address.
     * @param nonce The request nonce.
     * @param body The request body.
     *
     * @return The message signature.
     */
    protected String getAuthenticationSignature(URI address, String nonce, JSONObject body) {
        StringBuilder message = new StringBuilder("/api")
                .append(address.getPath())
                .append(nonce);

        if (body.length() > 0) {
            message.append(body);
        }

        try {
            Mac hmac = Mac.getInstance("HmacSHA384");
            Charset charset = StandardCharsets.US_ASCII;

            hmac.init(new SecretKeySpec(this.seckey.getBytes(charset), "HmacSHA384"));

            return Hex.encodeHexString(
                    hmac.doFinal(message.toString().getBytes(charset))
            );
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Get authentication headers.
     *
     * @param address The destination address.
     * @param body The request body.
     * @param charset The request charset.
     *
     * @return An unmodifiable collection of HTTP headers.
     */
    protected Collection<Header> getAuthenticationHeaders(URI address, JSONObject body, Charset charset) {
        String nonce = Long.toString(
                Instant.now().toEpochMilli()
        );

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
     * @param endpoint The destination endpoint.
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
     * @param body The request body.
     *
     * @return The response body.
     */
    protected String post(String endpoint, JSONObject body) {
        return this.post(endpoint, Collections.<NameValuePair>emptyList(), body, StandardCharsets.UTF_8);
    }

    /**
     * Send an API POST request.
     *
     * @param endpoint The destination endpoint.
     * @param parameters The request parameters.
     * @param body The request body.
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
     * @param body The request body.
     * @param charset The request charset.
     *
     * @return The response body.
     */
    protected String post(String endpoint, JSONObject body, Charset charset) {
        return this.post(endpoint, Collections.<NameValuePair>emptyList(), body, charset);
    }

    /**
     * Send an API POST request.
     *
     * @param endpoint The destination endpoint.
     * @param parameters The request parameters.
     * @param body The request body.
     * @param charset The request charset.
     *
     * @return The response body.
     */
    protected String post(String endpoint, Collection<NameValuePair> parameters, JSONObject body, Charset charset) {
        try {
            ContentType ctype = ContentType.APPLICATION_JSON.withCharset(charset);

            URI address = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.bitfinex.com")
                    .setPath(endpoint)
                    .build();

            RequestBuilder request = RequestBuilder
                    .post(address)
                    .setCharset(charset)
                    .addHeader(HttpHeaders.ACCEPT, ctype.getMimeType());

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
                request.addParameters(parameters.toArray(
                        new NameValuePair[parameters.size()]
                ));
            }

            return EntityUtils.toString(
                    this.client.execute(request.build()).getEntity()
            );
        } catch (URISyntaxException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) throws JSONException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            Example example = new Example(client);

            // Get active orders.
            System.out.println(example.post("/v2/auth/r/orders"));

            // Get account funding info.
            JSONObject payload = new JSONObject();

            payload.put("dir", 1);
            payload.put("rate", 800);
            payload.put("type", "EXCHANGE");
            payload.put("symbol", "tBTCUSD");

            System.out.println(example.post("/v2/auth/r/info/funding/fUSD", payload));

            // Calculate the average execution rate for Trading or Margin funding.
            Collection<NameValuePair> parameters = new ArrayList<>();

            parameters.add(new BasicNameValuePair("symbol", "tBTCUSD"));
            parameters.add(new BasicNameValuePair("amount", "1.123"));

            System.out.println(example.post("/v2/calc/trade/avg", parameters));
        } catch (IOException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

}
