package com.own.ai.vectorstore;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

@Component
public class InMemoryVectorStore implements VectorStore {

	private final Map<String, StoredDocument> documentStore = new ConcurrentHashMap<>();
	private final EmbeddingModel embeddingModel;

	public InMemoryVectorStore(EmbeddingModel embeddingModel) {
		this.embeddingModel = embeddingModel;
	}

	@Override
	public void add(List<Document> documents) {
		for (Document doc : documents) {
			try {
				EmbeddingResponse response = embeddingModel.embedForResponse(List.of(doc.getContent()));
				float[] embedding = response.getResult().getOutput();
				documentStore.put(doc.getId(), new StoredDocument(doc, embedding));
			} catch (Exception e) {
				throw new RuntimeException("Failed to embed document: " + doc.getId(), e);
			}
		}
	}

	@Override
	public Optional<Boolean> delete(List<String> idList) {
		idList.forEach(documentStore::remove);
		return Optional.of(true);
	}

	@Override
	public List<Document> similaritySearch(String query) {
		return similaritySearch(SearchRequest.query(query).withTopK(5));
	}

	@Override
	public List<Document> similaritySearch(SearchRequest request) {
		if (documentStore.isEmpty()) {
			return Collections.emptyList();
		}

		try {
			EmbeddingResponse response = embeddingModel.embedForResponse(List.of(request.getQuery()));
			float[] queryEmbedding = response.getResult().getOutput();

			return documentStore.values().stream()
					.map(stored -> new ScoredDocument(stored.document,
							cosineSimilarity(queryEmbedding, stored.embedding)))
					.filter(scored -> scored.score >= request.getSimilarityThreshold())
					.sorted(Comparator.comparingDouble((ScoredDocument sd) -> sd.score).reversed())
					.limit(request.getTopK()).map(scored -> scored.document).collect(Collectors.toList());

		} catch (Exception e) {
			throw new RuntimeException("Failed to perform similarity search", e);
		}
	}

	private double cosineSimilarity(float[] vectorA, float[] vectorB) {
		if (vectorA.length != vectorB.length) {
			throw new IllegalArgumentException("Vectors must have the same length");
		}

		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;

		for (int i = 0; i < vectorA.length; i++) {
			dotProduct += vectorA[i] * vectorB[i];
			normA += vectorA[i] * vectorA[i];
			normB += vectorB[i] * vectorB[i];
		}

		if (normA == 0.0 || normB == 0.0) {
			return 0.0;
		}

		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}

	public int size() {
		return documentStore.size();
	}

	public void clear() {
		documentStore.clear();
	}

	private record StoredDocument(Document document, float[] embedding) {
	}

	private record ScoredDocument(Document document, double score) {
	}
}
