//package com.own.spring_ai_project_gradle.service;
//
//import org.springframework.ai.OpenAIClient;
//import org.springframework.ai.model.CompletionRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//import org.springframework.ai.model.chat.ChatModel;
//import org.springframework.ai.model.chat.ChatResponse;
//import org.springframework.ai.openai.OpenAiChatProperties;
//
////@Service
////public class AIService {
////
////	@Autowired // Inject open ai clinet.
////	private org.springframework.ai.OpenAIClient openAIClient;
////	
////	
////
////	public String generateText(String prompt) {
////		// Create a request object for the AI model
////		CompletionRequest request = new CompletionRequest();
////		request.setPrompt(prompt);
////		request.setMaxTokens(100); // Limit the response to 100 tokens
////
////		// Call the OpenAI model and return the result
////		return openAIClient.createCompletion(request).getChoices().get(0).getText();
////	}
////	
////	private final OpenAiChatClient client;
////
////    public AIService(OpenAiChatClient client) {
////        this.client = client;
////    }
////
////    public String ask(String prompt) {
////        return client.call(prompt);
////    }
////}
//
//
//
