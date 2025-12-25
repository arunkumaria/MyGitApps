package com.own.ai.model;

import java.time.LocalDateTime;

public record Diagnosis(String id, String patientId, String icdCode, String description, String status,
		LocalDateTime diagnosisDate) {
}
