package com.own.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.service.ChatService;

@RestController
@RequestMapping("/api/test")
public class TestController {

	private final ChatService chatService;

	public TestController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping("/hello")
	public String hello() {
		return chatService.simpleChat("Say hello in a friendly way");
	}

	@PostMapping("/explain")
	public String explainCode(@RequestBody CodeRequest request) {
		return chatService.codeExplainer(request.code());
	}

	@PostMapping("/summarize")
	public String summarize(@RequestBody TextRequest request) {
		return chatService.summarize(request.text());
	}

	record CodeRequest(String code) {
	}

	record TextRequest(String text) {
	}
}
