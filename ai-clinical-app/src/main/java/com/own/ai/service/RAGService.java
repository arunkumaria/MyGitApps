package com.own.ai.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.own.ai.dto.ClinicalQueryRequest;
import com.own.ai.dto.ClinicalQueryResponse;

@Service
public class RAGService {

	private static final Logger log = LoggerFactory.getLogger(RAGService.class);

	private final VectorStore vectorStore;
	private final LLMRouterService llmRouter;

	public RAGService(VectorStore vectorStore, LLMRouterService llmRouter) {
		this.vectorStore = vectorStore;
		this.llmRouter = llmRouter;
	}

	public ClinicalQueryResponse queryClinicalData(ClinicalQueryRequest request) {
		log.info("Processing clinical query for patient: {}", request.patientId());

		// 1. Retrieve relevant documents using vector similarity
		SearchRequest searchRequest = SearchRequest.query(request.query()).withTopK(request.maxResults())
				.withSimilarityThreshold(0.7);

		List<Document> relevantDocs = vectorStore.similaritySearch(searchRequest);

		if (relevantDocs.isEmpty()) {
			return new ClinicalQueryResponse("No relevant clinical information found for this query.", List.of(), 0.0,
					request.llmProvider());
		}

		// 2. Extract context from documents
		List<String> context = relevantDocs.stream().map(Document::getContent).collect(Collectors.toList());

		// 3. Generate response using selected LLM
		String answer = llmRouter.generateWithContext(request.query(), context, request.llmProvider());

		// 4. Extract sources
		List<String> sources = relevantDocs.stream().map(doc -> {
			String noteType = (String) doc.getMetadata().getOrDefault("noteType", "Unknown");
			return "Document ID: " + doc.getId() + " (Type: " + noteType + ")";
		}).collect(Collectors.toList());

		return new ClinicalQueryResponse(answer, sources, 0.85, request.llmProvider());
	}

	@Async
	public void indexClinicalNote(Document document) {
		log.debug("Indexing clinical note: {}", document.getId());
		vectorStore.add(List.of(document));
	}
}