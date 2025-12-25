package com.own.ai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DocumentService {

	private final VectorStore vectorStore;

	public DocumentService(VectorStore vectorStore) {
		this.vectorStore = vectorStore;
	}

	public String addDocument(String content, Map<String, Object> metadata) {
		String id = UUID.randomUUID().toString();
		Document doc = new Document(id, content, metadata);
		vectorStore.add(List.of(doc));
		return id;
	}

	public String addDocument(String content) {
		return addDocument(content, new HashMap<>());
	}

	public List<Document> search(String query, int topK, double threshold) {
		SearchRequest request = SearchRequest.query(query).withTopK(topK).withSimilarityThreshold(threshold);

		return vectorStore.similaritySearch(request);
	}

	public List<Document> search(String query) {
		return search(query, 5, 0.7);
	}

	public void deleteDocuments(List<String> ids) {
		vectorStore.delete(ids);
	}
}