package net.surfm.crypto.bitfinex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = { "net.surfm" })
@SpringBootApplication
@PropertySource({ "classpath:temp.properties", "classpath:${env}.properties" })
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
