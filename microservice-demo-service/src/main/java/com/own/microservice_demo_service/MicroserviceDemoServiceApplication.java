package com.own.microservice_demo_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceDemoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceDemoServiceApplication.class, args);
	}

}
