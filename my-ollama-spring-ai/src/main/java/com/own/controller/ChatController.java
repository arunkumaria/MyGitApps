package com.own.controller;


import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {

	private final ChatModel chatModel;

	public ChatController(ChatModel chatModel) {
		this.chatModel = chatModel;
	}

	@PostMapping("/chat")
	public String chat(@RequestBody ChatRequest request) {
		return chatModel.call(request.message());
	}

	@PostMapping("/chat/detailed")
	public ChatResponse chatDetailed(@RequestBody ChatRequest request) {
		var response = chatModel.call(new Prompt(request.message()));
		return new ChatResponse(response.getResult().getOutput().getContent(), response.getMetadata().getModel());
	}

	record ChatRequest(String message) {
	}

	record ChatResponse(String content, String model) {
	}
}