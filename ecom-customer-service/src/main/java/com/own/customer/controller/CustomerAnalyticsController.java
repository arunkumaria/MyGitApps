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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer Analytics", description = "Customer analytics APIs protected by Resilience4j")
public class CustomerAnalyticsController {

	private final CustomerAnalyticsService customerAnalyticsService;

	public CustomerAnalyticsController(CustomerAnalyticsService customerAnalyticsService) {

		this.customerAnalyticsService = customerAnalyticsService;
	}

	@Operation(summary = "Get customer AI analytics", description = "Retrieves analytics for a customer from the AI Analytics "
			+ "service. Resilience4j Circuit Breaker, Retry and " + "TimeLimiter protect the downstream call. "
			+ "A fallback response is returned when the downstream " + "service is unavailable.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Analytics retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AiAnalyticsResponse.class))),
			@ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
			@ApiResponse(responseCode = "503", description = "AI Analytics service unavailable", content = @Content) })
	@GetMapping("/{id}/analytics")
	public CompletableFuture<ResponseEntity<AiAnalyticsResponse>> getAnalytics(

			@Parameter(description = "Customer UUID", required = true, example = "682cf0b9-9064-4d8d-86f4-2ec161a423a6") @PathVariable UUID id) {

		return customerAnalyticsService.getAnalytics(id).thenApply(ResponseEntity::ok);
	}
}