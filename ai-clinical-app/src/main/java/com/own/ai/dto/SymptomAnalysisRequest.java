package com.own.ai.dto;

import java.util.List;

public record SymptomAnalysisRequest(String patientId, List<String> symptoms, String clinicalContext) {
}
