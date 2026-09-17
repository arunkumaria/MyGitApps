package com.own.customer.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.own.customer.client.AiAnalyticsClient;
import com.own.customer.dto.AiAnalyticsResponse;

@Service
public class CustomerAnalyticsService {

	private final AiAnalyticsClient aiAnalyticsClient;

	public CustomerAnalyticsService(AiAnalyticsClient aiAnalyticsClient) {

		this.aiAnalyticsClient = aiAnalyticsClient;
	}

	public CompletableFuture<AiAnalyticsResponse> getAnalytics(UUID customerId) {

		return aiAnalyticsClient.getCustomerAnalytics(customerId);
	}
}
