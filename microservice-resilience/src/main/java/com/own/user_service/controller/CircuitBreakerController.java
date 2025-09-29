package com.own.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.user_service.service.SampleService;

@RestController
public class CircuitBreakerController {
	private final SampleService sampleService;

	public CircuitBreakerController(SampleService sampleService) {
		this.sampleService = sampleService;
	}

	@GetMapping("/test")
	public String testCircuitBreaker() {
		return sampleService.callExternalService();
	}
}