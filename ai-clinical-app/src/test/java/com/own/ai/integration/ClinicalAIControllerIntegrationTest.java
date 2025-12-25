package com.own.ai.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.own.ai.dto.ClinicalQueryRequest;
import com.own.ai.dto.DocumentRequest;
import com.own.ai.dto.SymptomAnalysisRequest;

@SpringBootTest
@AutoConfigureMockMvc
class ClinicalAIControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
		// Add test documents
		DocumentRequest doc1 = new DocumentRequest("Patient P001 has Type 2 Diabetes", Map.of("patientId", "P001"));

		mockMvc.perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(doc1)));
	}

	@Test
	void testClinicalQuery() throws Exception {
		// Given
		ClinicalQueryRequest request = new ClinicalQueryRequest("What is the patient's diagnosis?", "P001", 5,
				"OPENAI");

		// When & Then
		mockMvc.perform(post("/api/clinical/query").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.answer").isNotEmpty()).andExpect(jsonPath("$.provider").value("OPENAI"));
	}

	@Test
	void testSymptomAnalysis() throws Exception {
		// Given
		SymptomAnalysisRequest request = new SymptomAnalysisRequest("P001", List.of("fever", "cough"),
				"Patient has asthma");

		// When & Then
		mockMvc.perform(post("/api/clinical/symptoms/analyze").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.potentialDiagnoses").isArray()).andExpect(jsonPath("$.urgencyLevel").exists());
	}

	@Test
	void testDocumentSearch() throws Exception {
		// When & Then
		mockMvc.perform(get("/api/documents/search").param("query", "diabetes").param("topK", "5"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.query").value("diabetes"))
				.andExpect(jsonPath("$.results").isArray());
	}
}