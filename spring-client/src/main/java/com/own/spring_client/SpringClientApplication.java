package com.own.spring_client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.own.spring_client.service.HelloWorldClient;

@SpringBootApplication
public class SpringClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringClientApplication.class, args);
	}

	@Bean
	CommandLineRunner run(HelloWorldClient helloWorldClient) {
		return args -> {
			String response = helloWorldClient.getHelloWorld();
			System.out.println("Response from API: " + response);
		};
	}
}