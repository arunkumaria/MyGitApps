package com.own.spring_ai_project_gradle.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AIController {

	private final ChatClient chatClient;

	public AIController(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}

	@GetMapping("/ask")
	public String ask(@RequestParam("prompt") String prompt) {
		return chatClient.prompt().user(prompt).call().content();
	}
}
