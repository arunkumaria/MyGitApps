package com.own.ai.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.own.ai.dto.SymptomAnalysisRequest;
import com.own.ai.dto.SymptomAnalysisResponse;

@Service
public class ClinicalIntelligenceService {

	private static final Logger log = LoggerFactory.getLogger(ClinicalIntelligenceService.class);

	private final LLMRouterService llmRouter;
	private final ObjectMapper objectMapper;

	public ClinicalIntelligenceService(LLMRouterService llmRouter) {
		this.llmRouter = llmRouter;
		this.objectMapper = new ObjectMapper();
	}

	public SymptomAnalysisResponse analyzeSymptoms(SymptomAnalysisRequest request) {
		log.info("Analyzing symptoms for patient: {}", request.patientId());

		String prompt = buildSymptomAnalysisPrompt(request);
		String response = llmRouter.generateResponse(prompt, "OPENAI");

		return parseSymptomAnalysisResponse(response);
	}

	private String buildSymptomAnalysisPrompt(SymptomAnalysisRequest request) {
		StringBuilder prompt = new StringBuilder();
		prompt.append("You are a clinical decision support AI. Analyze the following case:\n\n");

		prompt.append("Current Symptoms:\n");
		request.symptoms().forEach(s -> prompt.append("- ").append(s).append("\n"));
		prompt.append("\n");

		if (request.clinicalContext() != null && !request.clinicalContext().isEmpty()) {
			prompt.append("Additional Context: ").append(request.clinicalContext()).append("\n\n");
		}

		prompt.append("Provide your analysis in JSON format with the following structure:\n");
		prompt.append("{\n");
		prompt.append("  \"potentialDiagnoses\": [\"diagnosis1\", \"diagnosis2\", \"diagnosis3\"],\n");
		prompt.append("  \"recommendedTests\": [\"test1\", \"test2\"],\n");
		prompt.append("  \"urgencyLevel\": \"ROUTINE|URGENT|EMERGENT\",\n");
		prompt.append("  \"reasoning\": \"explanation of your analysis\"\n");
		prompt.append("}\n\n");
		prompt.append("Respond ONLY with valid JSON, no additional text.");

		return prompt.toString();
	}

	private SymptomAnalysisResponse parseSymptomAnalysisResponse(String response) {
		try {
			// Clean response
			String cleanJson = response.trim();
			if (cleanJson.startsWith("```json")) {
				cleanJson = cleanJson.substring(7);
			}
			if (cleanJson.startsWith("```")) {
				cleanJson = cleanJson.substring(3);
			}
			if (cleanJson.endsWith("```")) {
				cleanJson = cleanJson.substring(0, cleanJson.length() - 3);
			}
			cleanJson = cleanJson.trim();

			JsonNode root = objectMapper.readTree(cleanJson);

			List<String> diagnoses = new ArrayList<>();
			root.get("potentialDiagnoses").forEach(node -> diagnoses.add(node.asText()));

			List<String> tests = new ArrayList<>();
			root.get("recommendedTests").forEach(node -> tests.add(node.asText()));

			return new SymptomAnalysisResponse(diagnoses, tests, root.get("urgencyLevel").asText(),
					root.get("reasoning").asText());
		} catch (Exception e) {
			log.error("Failed to parse symptom analysis response", e);
			return new SymptomAnalysisResponse(List.of("Unable to analyze - please consult a healthcare provider"),
					List.of(), "URGENT", "Error processing analysis: " + e.getMessage());
		}
	}
}
