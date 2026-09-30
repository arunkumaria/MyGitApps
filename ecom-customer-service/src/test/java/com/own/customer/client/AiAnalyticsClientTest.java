package com.own.customer.client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.own.customer.config.AiAnalyticsProperties;
import com.own.customer.dto.AiAnalyticsResponse;

class AiAnalyticsClientTest {

	@Test
	void fallback_shouldReturnFallbackResponse() {

		UUID customerId = UUID.randomUUID();

		AiAnalyticsProperties properties = new AiAnalyticsProperties();

		properties.setBaseUrl("http://localhost:8082");

		AiAnalyticsClient client = new AiAnalyticsClient(null, properties);

		CompletableFuture<AiAnalyticsResponse> future = client.fallback(customerId,
				new RuntimeException("Service unavailable"));

		AiAnalyticsResponse response = future.join();

		assertNotNull(response);

		assertEquals(customerId, response.getCustomerId());

		assertEquals("UNKNOWN", response.getSegment());

		assertEquals("FALLBACK", response.getSource());

		assertEquals("AI Analytics service is currently unavailable", response.getRecommendation());
	}
}
