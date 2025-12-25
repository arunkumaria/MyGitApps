package com.own.ai.dto;

import java.util.List;

import org.springframework.ai.document.Document;

public record SearchResponse(String query, int resultCount, List<Document> results) {
}
