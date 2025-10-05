package com.own.spring_client.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloWorldClient {

	private final RestTemplate restTemplate;

	public HelloWorldClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getHelloWorld() {
		String url = "http://localhost:8080/api/hello";
		return restTemplate.getForObject(url, String.class);
	}
}
