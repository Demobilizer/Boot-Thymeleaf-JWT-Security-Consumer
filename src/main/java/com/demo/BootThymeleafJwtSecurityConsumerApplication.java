package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BootThymeleafJwtSecurityConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootThymeleafJwtSecurityConsumerApplication.class, args);
	}

	@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
	
}
