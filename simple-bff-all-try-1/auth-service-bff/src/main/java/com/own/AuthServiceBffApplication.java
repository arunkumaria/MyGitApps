package com.own;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceBffApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceBffApplication.class, args);
	}

}
