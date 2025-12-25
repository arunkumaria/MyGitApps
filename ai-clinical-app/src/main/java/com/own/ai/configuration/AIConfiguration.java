package com.own.ai.configuration;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.api.AnthropicApi;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AIConfiguration {
    
    @Value("${spring.ai.openai.api-key}")
    private String openaiApiKey;
    
    @Value("${spring.ai.anthropic.api-key:}")
    private String anthropicApiKey;
    
    @Bean
    @Primary
    public OpenAiChatModel openAiChatModel() {
        var openAiApi = new OpenAiApi(openaiApiKey);
        return new OpenAiChatModel(openAiApi);
    }
    
    @Bean
    public AnthropicChatModel anthropicChatModel() {
        if (anthropicApiKey != null && !anthropicApiKey.isEmpty()) {
            var anthropicApi = new AnthropicApi(anthropicApiKey);
            return new AnthropicChatModel(anthropicApi);
        }
        return null;
    }
    
    @Bean
    public ChatClient chatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
}