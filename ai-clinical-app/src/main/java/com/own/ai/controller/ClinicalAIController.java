package com.own.ai.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.ai.dto.ClinicalQueryRequest;
import com.own.ai.dto.ClinicalQueryResponse;
import com.own.ai.dto.SymptomAnalysisRequest;
import com.own.ai.dto.SymptomAnalysisResponse;
import com.own.ai.service.ClinicalIntelligenceService;
import com.own.ai.service.RAGService;

@RestController
@RequestMapping("/api/clinical")
@CrossOrigin(origins = "*")
public class ClinicalAIController {

	private static final Logger log = LoggerFactory.getLogger(ClinicalAIController.class);

	private final RAGService ragService;
	private final ClinicalIntelligenceService intelligenceService;

	public ClinicalAIController(RAGService ragService, ClinicalIntelligenceService intelligenceService) {
		this.ragService = ragService;
		this.intelligenceService = intelligenceService;
	}

	@PostMapping("/query")
	public ResponseEntity<ClinicalQueryResponse> queryClinicalData(@RequestBody ClinicalQueryRequest request) {
		log.info("Received clinical query: {}", request.query());
		ClinicalQueryResponse response = ragService.queryClinicalData(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/symptoms/analyze")
	public ResponseEntity<SymptomAnalysisResponse> analyzeSymptoms(@RequestBody SymptomAnalysisRequest request) {
		log.info("Analyzing symptoms for patient: {}", request.patientId());
		SymptomAnalysisResponse response = intelligenceService.analyzeSymptoms(request);
		return ResponseEntity.ok(response);
	}
}