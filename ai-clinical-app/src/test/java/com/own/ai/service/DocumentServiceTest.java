package com.own.ai.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DocumentServiceTest {

	@Autowired
	private DocumentService documentService;

	@Autowired
	private com.own.ai.vectorstore.InMemoryVectorStore vectorStore;

	@BeforeEach
	void setUp() {
		vectorStore.clear();
	}

	@Test
	void testAddDocument() {
		// Given
		Map<String, Object> metadata = new HashMap<>();
		metadata.put("patientId", "P001");
		metadata.put("type", "clinical_note");

		// When
		String docId = documentService.addDocument("Patient diagnosed with diabetes", metadata);

		// Then
		assertThat(docId).isNotNull();
		assertThat(vectorStore.size()).isEqualTo(1);
	}

	@Test
	void testSearchDocuments() {
		// Given
		documentService.addDocument("Patient has Type 2 Diabetes Mellitus", Map.of("patientId", "P001"));

		documentService.addDocument("Patient diagnosed with hypertension", Map.of("patientId", "P002"));

		// When
		List<Document> results = documentService.search("diabetes");

		// Then
		assertThat(results).isNotEmpty();
		assertThat(results.get(0).getContent()).containsIgnoringCase("diabetes");
	}

	@Test
	void testSearchWithCustomParameters() {
		// Given
		for (int i = 0; i < 10; i++) {
			documentService.addDocument("Clinical note " + i, Map.of());
		}

		// When
		List<Document> results = documentService.search("clinical", 3, 0.5);

		// Then
		assertThat(results).hasSizeLessThanOrEqualTo(3);
	}
}
