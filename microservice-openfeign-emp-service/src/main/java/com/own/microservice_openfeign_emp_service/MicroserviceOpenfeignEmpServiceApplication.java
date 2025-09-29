package com.own.microservice_openfeign_emp_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroserviceOpenfeignEmpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceOpenfeignEmpServiceApplication.class, args);
	}

}
