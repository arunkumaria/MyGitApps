package com.own.spring_ai_ollama.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

	// This bean is automatically created by the Ollama starter,
	// assuming your properties are correct.
	private final OllamaChatModel ollamaChatModel;

	public ChatClientConfiguration(OllamaChatModel ollamaChatModel) {
		this.ollamaChatModel = ollamaChatModel;
	}

	@Bean
	public ChatClient chatClient() {
		return ChatClient.builder(ollamaChatModel).build();
	}
}