package com.own.ai.service;

import org.springframework.stereotype.Service;

import com.own.ai.dto.ApiSpecResponse;
import com.own.ai.util.PromptBuilder;

@Service
public class RagService {

	private final ChromaService chromaService;
	private final OpenAiService openAiService;

	public RagService(ChromaService chromaService, OpenAiService openAiService) {
		this.chromaService = chromaService;
		this.openAiService = openAiService;
	}

	public ApiSpecResponse generateSpec(String userInput) {

		// 1️⃣ Retrieve context from ChromaDB
		String context = chromaService.search(userInput);

		// 2️⃣ Build prompt
		String prompt = PromptBuilder.build(userInput, context);

		// 3️⃣ Call OpenAI
		String spec = openAiService.generate(prompt);

		return new ApiSpecResponse(spec);
	}
}
