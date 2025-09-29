package com.own.microservice_openfeign_address_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroserviceOpenfeignAddressServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceOpenfeignAddressServiceApplication.class, args);
	}

}
