package net.surfm.crypto.bitfinex.api;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import net.surfm.exception.SurfmRuntimeException;
import net.surfm.lambda.Action;

@Service
public class ApiCall {
	
	@Inject
	private Provider<ApiClient> apiClientProvider;
	@Inject
	private Provider<ApiMethod> apiMethodProvider;
	
	public void run(Action._1<ApiMethod> task) {
		 try (CloseableHttpClient client = HttpClients.createDefault()) {
			 ApiClient ac = apiClientProvider.get().init(client);
			 ApiMethod am =apiMethodProvider.get().init(ac);
			 task.accept(am);
	        } catch (IOException ex) {
	            throw new SurfmRuntimeException(ex);
	        }		
		
	}

}
