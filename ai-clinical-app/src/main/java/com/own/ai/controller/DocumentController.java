package com.own.ai.controller;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.ai.dto.DocumentRequest;
import com.own.ai.dto.DocumentResponse;
import com.own.ai.dto.SearchResponse;
import com.own.ai.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentController {

	private final DocumentService documentService;

	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@PostMapping
	public ResponseEntity<DocumentResponse> addDocument(@RequestBody DocumentRequest request) {

		String id = documentService.addDocument(request.content(), request.metadata());

		return ResponseEntity.ok(new DocumentResponse(id, "Document indexed successfully"));
	}

	@GetMapping("/search")
	public ResponseEntity<SearchResponse> search(@RequestParam String query, @RequestParam(defaultValue = "5") int topK,
			@RequestParam(defaultValue = "0.7") double threshold) {

		List<Document> results = documentService.search(query, topK, threshold);

		return ResponseEntity.ok(new SearchResponse(query, results.size(), results));
	}

	@DeleteMapping
	public ResponseEntity<String> deleteDocuments(@RequestBody List<String> ids) {
		documentService.deleteDocuments(ids);
		return ResponseEntity.ok("Documents deleted successfully");
	}
}
