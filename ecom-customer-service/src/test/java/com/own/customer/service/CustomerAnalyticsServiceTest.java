package com.own.customer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.own.customer.client.AiAnalyticsClient;
import com.own.customer.dto.AiAnalyticsResponse;

@ExtendWith(MockitoExtension.class)
class CustomerAnalyticsServiceTest {

	@Mock
	private AiAnalyticsClient aiAnalyticsClient;

	@InjectMocks
	private CustomerAnalyticsService customerAnalyticsService;

	@Test
	void getCustomerAnalytics_shouldReturnAnalytics() {

		UUID customerId = UUID.randomUUID();

		AiAnalyticsResponse response = new AiAnalyticsResponse();

		response.setCustomerId(customerId);
		response.setSegment("ENTERPRISE");
		response.setRecommendation("High-value customer");
		response.setSource("AI");

		when(aiAnalyticsClient.getCustomerAnalytics(customerId))
				.thenReturn(CompletableFuture.completedFuture(response));

		CompletableFuture<AiAnalyticsResponse> result = customerAnalyticsService.getAnalytics(customerId);

		AiAnalyticsResponse actual = result.join();

		assertNotNull(actual);

		assertEquals(customerId, actual.getCustomerId());

		assertEquals("ENTERPRISE", actual.getSegment());

		assertEquals("AI", actual.getSource());

		verify(aiAnalyticsClient).getCustomerAnalytics(customerId);
	}
}