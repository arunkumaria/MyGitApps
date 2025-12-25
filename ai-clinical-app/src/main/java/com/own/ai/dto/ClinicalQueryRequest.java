package com.own.ai.dto;

public record ClinicalQueryRequest(String query, String patientId, int maxResults, String llmProvider) {
}