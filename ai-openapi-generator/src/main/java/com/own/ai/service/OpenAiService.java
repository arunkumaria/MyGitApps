package com.own.ai.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenAiService {

	private final WebClient webClient;

	@Value("${openai.api-key}")
	private String apiKey;

	public OpenAiService(WebClient openAiWebClient) {
		this.webClient = openAiWebClient;
	}

	public String generate(String prompt) {
		Map<String, Object> body = Map.of("model", "gpt-4.1-mini", "input", prompt);

		return webClient.post().uri("/responses") // ✅ CORRECT ENDPOINT
				.header("Authorization", "Bearer " + apiKey).bodyValue(body).retrieve().bodyToMono(String.class)
				.block();
	}
}
