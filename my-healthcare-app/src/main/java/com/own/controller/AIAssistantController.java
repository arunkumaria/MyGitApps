package com.own.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.AIResponseDTO;
import com.own.model.MedicalQuery;
import com.own.service.OllamaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIAssistantController {

	private final OllamaService ollamaService;

	@PostMapping("/ask")
	public ResponseEntity<AIResponseDTO> askMedicalQuestion(@RequestBody MedicalQuery query) {
		AIResponseDTO response = ollamaService.generateMedicalResponse(query.getQuery(), query.getPatientContext());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/health")
	public ResponseEntity<String> checkOllamaHealth() {
		boolean available = ollamaService.isOllamaAvailable();
		return ResponseEntity.ok(available ? "Ollama is available" : "Ollama is not available");
	}
}
