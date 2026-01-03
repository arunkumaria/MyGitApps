package com.own.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

	private final ChatModel chatModel;

	public ChatService(ChatModel chatModel) {
		this.chatModel = chatModel;
	}

	public String simpleChat(String message) {
		return chatModel.call(message);
	}

	public String chatWithSystem(String systemPrompt, String userMessage) {
		var systemMsg = new SystemMessage(systemPrompt);
		var userMsg = new UserMessage(userMessage);
		var prompt = new Prompt(List.of(systemMsg, userMsg));

		return chatModel.call(prompt).getResult().getOutput().getContent();
	}

	public String codeExplainer(String code) {
		String systemPrompt = "You are an expert programmer. Explain code clearly.";
		String userPrompt = "Explain this code:\n\n" + code;
		return chatWithSystem(systemPrompt, userPrompt);
	}

	public String summarize(String text) {
		return simpleChat("Summarize this text in 3 sentences:\n\n" + text);
	}
}