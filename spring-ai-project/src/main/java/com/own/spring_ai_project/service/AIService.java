package com.own.spring_ai_project.service;

//
//import org.springframework.stereotype.Service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.client.WebClientResponseException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.util.*;
//
////@Service
////public class AIService {
//
////	private final WebClient webClient;
//
////	private final ObjectMapper objectMapper;
////
////	public AIService(@Value("${spring.ai.openai.api-key}") String apiKey) {
////		this.webClient = WebClient.builder().baseUrl("https://api.openai.com/v1/chat/completions")
////				.defaultHeader("Authorization", "Bearer " + apiKey).build();
////		this.objectMapper = new ObjectMapper();
////	}
////
////	public String generateText(String prompt) {
////		try {
////			// Create the JSON body
////			String body = "{ \"model\": \"gpt-3.5-turbo\", " + "\"messages\": [{\"role\": \"user\", \"content\": \""
////					+ prompt + "\"}] }";
////
////			// Call OpenAI API
////			String response = webClient.post().bodyValue(body).retrieve().bodyToMono(String.class).block();
////
////			// Parse response
////			JsonNode root = objectMapper.readTree(response);
////			return root.get("choices").get(0).get("message").get("content").asText();
////
////		} catch (WebClientResponseException e) {
////			return "Error calling OpenAI API: " + e.getMessage();
////		} catch (Exception e) {
////			return "Unexpected error: " + e.getMessage();
////		}
////	}
////}
//
////import java.util.*;
//
////import com.fasterxml.jackson.databind.ObjectMapper;
//
////
////@Service
////public class AIService {
////
////	private final WebClient webClient;
////
////	private final ObjectMapper objectMapper;
////
////	public AIService(@Value("${spring.ai.openai.api-key}") String apiKey) {
////		this.webClient = WebClient.builder().baseUrl("https://api.openai.com/v1/chat/completions")
////				.defaultHeader("Authorization", "Bearer " + apiKey).build();
////		this.objectMapper = new ObjectMapper();
////	}
////
////	public String generateText(String prompt) {
////
////		try {
////			// Build request body safely
////			Map<String, Object> message = Map.of("role", "user", "content", prompt);
////			Map<String, Object> body = Map.of("model", "gpt-3.5-turbo", "messages", List.of(message));
////
////			// Convert to JSON
////			String jsonBody = objectMapper.writeValueAsString(body);
////
////			// Call OpenAI API
////			String response = webClient.post().bodyValue(jsonBody).retrieve().bodyToMono(String.class).block();
////
////			// Parse response
////			JsonNode root = objectMapper.readTree(response);
////			return root.get("choices").get(0).get("message").get("content").asText();
////
////		} catch (WebClientResponseException e) {
////			return "Error calling OpenAI API: " + e.getResponseBodyAsString();
////		} catch (Exception e) {
////			return "Unexpected error: " + e.getMessage();
////		}
////	}
////
////}
//
//
//
@Service
public class AIService {

	private final WebClient webClient;
	private final ObjectMapper objectMapper;

	public AIService(@Value("${spring.ai.openai.api-key}") String apiKey) {
		this.webClient = WebClient.builder().baseUrl("https://api.openai.com/v1/chat/completions")
				.defaultHeader("Authorization", "Bearer " + apiKey).defaultHeader("Content-Type", "application/json") // Important!
				.build();
		this.objectMapper = new ObjectMapper();
	}

	public String generateText(String prompt) {
		try {
			// Build request body as Map
			Map<String, Object> message = Map.of("role", "user", "content", prompt);
			Map<String, Object> body = Map.of("model", "gpt-3.5-turbo", "messages", List.of(message));

			// Convert Map to JSON string
			String jsonBody = objectMapper.writeValueAsString(body);

			// Call OpenAI API
			String response = webClient.post().bodyValue(jsonBody).retrieve().bodyToMono(String.class).block();

			// Parse response
			JsonNode root = objectMapper.readTree(response);
			return root.get("choices").get(0).get("message").get("content").asText();

		} catch (WebClientResponseException e) {
			// Show full API error
			return "Error calling OpenAI API: " + e.getResponseBodyAsString();
		} catch (Exception e) {
			return "Unexpected error: " + e.getMessage();
		}
	}
}
