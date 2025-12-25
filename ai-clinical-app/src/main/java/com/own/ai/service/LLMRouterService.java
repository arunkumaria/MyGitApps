package com.own.ai.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class LLMRouterService {

	private static final Logger log = LoggerFactory.getLogger(LLMRouterService.class);

	private final OpenAiChatModel openAiModel;
	private final AnthropicChatModel claudeModel;

	public LLMRouterService(OpenAiChatModel openAiModel, AnthropicChatModel claudeModel) {
		this.openAiModel = openAiModel;
		this.claudeModel = claudeModel;
	}

	public String generateResponse(String prompt, String provider) {
		log.debug("Generating response with provider: {}", provider);

		ChatModel model = selectModel(provider);
		ChatResponse response = model.call(new Prompt(prompt));

		return response.getResult().getOutput().getContent();
	}

	public String generateWithContext(String query, List<String> context, String provider) {
		String enrichedPrompt = buildContextualPrompt(query, context);
		return generateResponse(enrichedPrompt, provider);
	}

	private ChatModel selectModel(String provider) {
		return switch (provider.toUpperCase()) {
		case "OPENAI", "GPT" -> openAiModel;
		case "CLAUDE", "ANTHROPIC" -> {
			if (claudeModel == null) {
				log.warn("Claude model not configured, falling back to OpenAI");
				yield openAiModel;
			}
			yield claudeModel;
		}
		case "GEMINI" -> {
			log.warn("Gemini not configured, falling back to OpenAI");
			yield openAiModel;
		}
		default -> {
			log.warn("Unknown provider {}, using OpenAI", provider);
			yield openAiModel;
		}
		};
	}

	private String buildContextualPrompt(String query, List<String> context) {
		StringBuilder sb = new StringBuilder();
		sb.append("Context information:\n\n");

		for (int i = 0; i < context.size(); i++) {
			sb.append(i + 1).append(". ").append(context.get(i)).append("\n\n");
		}

		sb.append("---\n\n");
		sb.append("Based on the above context, answer this query: ").append(query);
		sb.append("\n\nProvide a clear, concise answer based only on the context provided.");

		return sb.toString();
	}
}
