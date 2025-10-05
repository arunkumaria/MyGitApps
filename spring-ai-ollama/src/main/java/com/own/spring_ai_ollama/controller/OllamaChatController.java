package com.own.spring_ai_ollama.controller;
	
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllamaChatController {

	private final ChatClient chatClient;

	public OllamaChatController(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	@GetMapping("/chat")
	public String chatWithOllama(@RequestParam(value = "prompt", defaultValue = "What is Spring Boot?") String prompt) {
		return chatClient.prompt().user(prompt).call().content();
	}
}
