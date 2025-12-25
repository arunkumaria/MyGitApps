package com.own.ai.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.own.ai.dto.ClinicalQueryRequest;
import com.own.ai.dto.ClinicalQueryResponse;

@SpringBootTest
class RAGServiceTest {

	@Autowired
	private RAGService ragService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private com.own.ai.vectorstore.InMemoryVectorStore vectorStore;

	@BeforeEach
	void setUp() {
		vectorStore.clear();

		// Add test clinical notes
		documentService.addDocument(
				"Patient P001 diagnosed with Type 2 Diabetes. HbA1c: 8.5%. Started on Metformin 500mg BID.",
				Map.of("patientId", "P001", "noteType", "SOAP"));

		documentService.addDocument("Follow-up for P001: HbA1c improved to 7.2%. Continue Metformin.",
				Map.of("patientId", "P001", "noteType", "FOLLOW_UP"));
	}

	@Test
	void testClinicalQuery() {
		// Given
		ClinicalQueryRequest request = new ClinicalQueryRequest("What is the patient's diabetes status?", "P001", 5,
				"OPENAI");

		// When
		ClinicalQueryResponse response = ragService.queryClinicalData(request);

		// Then
		assertThat(response.answer()).isNotEmpty();
		assertThat(response.sources()).isNotEmpty();
		assertThat(response.provider()).isEqualTo("OPENAI");
		assertThat(response.confidence()).isGreaterThan(0.0);
	}

	@Test
	void testQueryWithNoResults() {
		// Given - query for non-existent patient
		ClinicalQueryRequest request = new ClinicalQueryRequest("What is the patient's status?", "P999", 5, "OPENAI");

		// When
		ClinicalQueryResponse response = ragService.queryClinicalData(request);

		// Then
		assertThat(response.answer()).contains("No relevant");
		assertThat(response.sources()).isEmpty();
	}
}
