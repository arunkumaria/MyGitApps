package com.own.ai.dto;

import java.util.Map;

public record DocumentRequest(String content, Map<String, Object> metadata) {
}