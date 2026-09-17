package com.own.customer.client;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.own.customer.config.AiAnalyticsProperties;
import com.own.customer.dto.AiAnalyticsResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Component
public class AiAnalyticsClient {

	private final RestClient restClient;
	private final AiAnalyticsProperties properties;

	public AiAnalyticsClient(RestClient restClient, AiAnalyticsProperties properties) {

		this.restClient = restClient;
		this.properties = properties;
	}

	@CircuitBreaker(name = "aiAnalyticsService", fallbackMethod = "fallback")
	@Retry(name = "aiAnalyticsService")
	@TimeLimiter(name = "aiAnalyticsService")
	public CompletableFuture<AiAnalyticsResponse> getCustomerAnalytics(UUID customerId) {

		return CompletableFuture.supplyAsync(
				() -> restClient.get().uri(properties.getBaseUrl() + "/api/v1/analytics/customers/{id}", customerId)
						.retrieve().body(AiAnalyticsResponse.class));
	}

	public CompletableFuture<AiAnalyticsResponse> fallback(UUID customerId, Throwable throwable) {

		AiAnalyticsResponse response = new AiAnalyticsResponse();

		response.setCustomerId(customerId);
		response.setSegment("UNKNOWN");
		response.setRecommendation("AI Analytics service is currently unavailable");
		response.setSource("FALLBACK");

		return CompletableFuture.completedFuture(response);
	}
}