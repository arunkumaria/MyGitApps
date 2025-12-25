package com.own.ai.model;

import java.time.LocalDateTime;
import java.util.Map;

public record ClinicalNote(String id, String patientId, String content, String noteType, String providerId,
		LocalDateTime createdAt, Map<String, Object> metadata) {
}