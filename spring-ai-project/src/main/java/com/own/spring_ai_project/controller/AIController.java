package com.own.spring_ai_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.own.spring_ai_project.service.AIService;

@RestController
@RequestMapping("/api/ai")
public class AIController {

	@Autowired
	private AIService aiService;

	@PostMapping("/generate")
	public String generateText(@RequestBody String prompt) {
		return aiService.generateText(prompt);
	}
}
