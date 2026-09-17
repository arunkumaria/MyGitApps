package com.own.customer.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.customer.dto.AiAnalyticsResponse;
import com.own.customer.service.CustomerAnalyticsService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerAnalyticsController {

	private final CustomerAnalyticsService customerAnalyticsService;

	public CustomerAnalyticsController(CustomerAnalyticsService customerAnalyticsService) {

		this.customerAnalyticsService = customerAnalyticsService;
	}

	@GetMapping("/{id}/analytics")
	public CompletableFuture<ResponseEntity<AiAnalyticsResponse>> getAnalytics(@PathVariable UUID id) {

		return customerAnalyticsService.getAnalytics(id).thenApply(ResponseEntity::ok);
	}
}