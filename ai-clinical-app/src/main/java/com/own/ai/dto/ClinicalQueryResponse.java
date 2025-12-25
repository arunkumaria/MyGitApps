package com.own.ai.dto;

import java.util.List;

public record ClinicalQueryResponse(String answer, List<String> sources, double confidence, String provider) {
}