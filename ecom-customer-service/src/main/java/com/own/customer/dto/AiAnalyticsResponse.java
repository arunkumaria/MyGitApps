package com.own.customer.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned by the AI Analytics service")
public class AiAnalyticsResponse {

	@Schema(description = "Customer UUID", example = "682cf0b9-9064-4d8d-86f4-2ec161a423a6")
	private UUID customerId;

	@Schema(description = "Customer segment identified by AI analytics", example = "PREMIUM")
	private String segment;

	@Schema(description = "AI-generated customer recommendation", example = "Customer is likely to respond to premium campaigns")
	private String recommendation;

	@Schema(description = "Source of the response", example = "AI_ANALYTICS")
	private String source;

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}