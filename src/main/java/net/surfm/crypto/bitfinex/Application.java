package net.surfm.crypto.bitfinex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	
	@Bean("Bitfinex")
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate ans= builder.build();
		return ans;
	}	

}
