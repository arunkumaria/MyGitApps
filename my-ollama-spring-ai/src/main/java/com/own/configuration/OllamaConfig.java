//package com.own.configuration;
//
//
//
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.ollama.OllamaChatModel;
//import org.springframework.ai.ollama.api.OllamaApi;
//import org.springframework.ai.ollama.api.OllamaOptions;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OllamaConfig {
//
//	@Value("${spring.ai.ollama.base-url}")
//	private String baseUrl;
//
//	@Bean
//	public OllamaApi ollamaApi() {
//		return new OllamaApi(baseUrl);
//	}
//
//	@Bean
//	public ChatModel ollamaChatModel(OllamaApi ollamaApi) {
//		return OllamaChatModel.builder().withOllamaApi(ollamaApi) // Changed from .ollamaApi() to .withOllamaApi()
//				.withDefaultOptions(OllamaOptions.builder().withModel("llama3.2").withTemperature(0.7).build()).build();
//	}
//}