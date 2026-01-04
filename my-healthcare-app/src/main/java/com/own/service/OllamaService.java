package com.own.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.own.dto.AIResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OllamaService {

	private final RestTemplate ollamaRestTemplate;

	@Value("${ollama.model}")
	private String model;

	public AIResponseDTO generateMedicalResponse(String prompt, String patientContext) {
		long startTime = System.currentTimeMillis();

		String enhancedPrompt = buildMedicalPrompt(prompt, patientContext);

		Map<String, Object> request = Map.of("model", model, "prompt", enhancedPrompt, "stream", false, "options",
				Map.of("temperature", 0.7));

		try {
			Map<String, Object> response = ollamaRestTemplate.postForObject("/api/generate", request, Map.class);

			String responseText = response != null ? (String) response.get("response") : "No response generated";

			long processingTime = System.currentTimeMillis() - startTime;

			return new AIResponseDTO(responseText, model, processingTime);
		} catch (Exception e) {
			log.error("Error calling Ollama API", e);
			throw new RuntimeException("Failed to generate AI response", e);
		}
	}

	private String buildMedicalPrompt(String query, String patientContext) {
		StringBuilder prompt = new StringBuilder();
		prompt.append("You are a medical AI assistant. Please provide helpful, accurate information. ");
		prompt.append(
				"Remember: This is for informational purposes only and not a substitute for professional medical advice.\n\n");

		if (patientContext != null && !patientContext.isEmpty()) {
			prompt.append("Patient Context: ").append(patientContext).append("\n\n");
		}

		prompt.append("Question: ").append(query).append("\n\n");
		prompt.append("Answer:");

		return prompt.toString();
	}

	public boolean isOllamaAvailable() {
		try {
			ollamaRestTemplate.getForObject("/api/tags", Map.class);
			return true;
		} catch (Exception e) {
			log.error("Ollama is not available", e);
			return false;
		}
	}
}
