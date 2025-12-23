package com.own.ai.util;

public class PromptBuilder {

	public static String build(String input, String context) {
		return """
				You are an expert backend architect.
				Generate a production-ready OpenAPI 3.0 specification.

				Context:
				%s

				Requirement:
				%s

				Include:
				- Endpoints
				- Request/Response schemas
				- HTTP status codes
				- Error responses
				""".formatted(context, input);
	}
}
