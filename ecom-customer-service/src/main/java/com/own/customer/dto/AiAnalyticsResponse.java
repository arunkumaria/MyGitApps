package com.own.customer.dto;

import java.util.UUID;

public class AiAnalyticsResponse {

	private UUID customerId;

	private String segment;

	private String recommendation;

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
