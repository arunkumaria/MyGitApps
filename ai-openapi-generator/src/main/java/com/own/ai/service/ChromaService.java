package com.own.ai.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ChromaService {

	private final WebClient webClient;

	public ChromaService(WebClient.Builder builder, @Value("${chroma.base-url}") String baseUrl) {
		this.webClient = builder.baseUrl(baseUrl).build();
	}

	public String search(String query) {
		// Simplified retrieval
		return webClient.post().uri("/query").bodyValue(Map.of("query", query)).retrieve().bodyToMono(String.class)
				.block();
	}
}
