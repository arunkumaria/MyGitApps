package com.own.ai.vectorstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class InMemoryVectorStoreTest {

	@Autowired
	private InMemoryVectorStore vectorStore;

	@Autowired
	private EmbeddingModel embeddingModel;

	@BeforeEach
	void setUp() {
		vectorStore.clear();
	}

	@Test
	void testAddAndSearchDocuments() {
		// Given
		Map<String, Object> metadata = new HashMap<>();
		metadata.put("patientId", "P001");
		metadata.put("noteType", "SOAP");

		Document doc1 = new Document("Patient diagnosed with Type 2 Diabetes. Prescribed Metformin 500mg.", metadata);

		Document doc2 = new Document("Patient presents with hypertension. Started on Lisinopril.", metadata);

		// When
		vectorStore.add(List.of(doc1, doc2));

		// Then
		assertThat(vectorStore.size()).isEqualTo(2);

		// Search for diabetes-related content
		List<Document> results = vectorStore.similaritySearch("diabetes medication");

		assertThat(results).isNotEmpty();
		assertThat(results.get(0).getContent()).contains("Diabetes");
	}

	@Test
	void testSimilarityThreshold() {
		// Given
		Document doc = new Document("Patient has acute bronchitis");
		vectorStore.add(List.of(doc));

		// When - search with high threshold
		SearchRequest highThreshold = SearchRequest.query("bronchitis").withSimilarityThreshold(0.9);

		SearchRequest lowThreshold = SearchRequest.query("completely unrelated query").withSimilarityThreshold(0.3);

		// Then
		List<Document> highResults = vectorStore.similaritySearch(highThreshold);
		List<Document> lowResults = vectorStore.similaritySearch(lowThreshold);

		assertThat(highResults).isNotEmpty();
		assertThat(lowResults.size()).isLessThanOrEqualTo(1);
	}

	@Test
	void testTopKResults() {
		// Given - add 10 documents
		for (int i = 0; i < 10; i++) {
			Document doc = new Document("Clinical note number " + i);
			vectorStore.add(List.of(doc));
		}

		// When - search with topK=5
		SearchRequest request = SearchRequest.query("clinical").withTopK(5);

		List<Document> results = vectorStore.similaritySearch(request);

		// Then
		assertThat(results).hasSize(5);
	}

	@Test
	void testDeleteDocuments() {
		// Given
		Document doc1 = new Document("doc1", "Content 1", Map.of());
		Document doc2 = new Document("doc2", "Content 2", Map.of());

		vectorStore.add(List.of(doc1, doc2));
		assertThat(vectorStore.size()).isEqualTo(2);

		// When
		vectorStore.delete(List.of("doc1"));

		// Then
		assertThat(vectorStore.size()).isEqualTo(1);
	}
}
