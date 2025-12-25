package com.own.ai.dto;

import java.util.List;

public record SymptomAnalysisResponse(List<String> potentialDiagnoses, List<String> recommendedTests,
		String urgencyLevel, String reasoning) {
}
