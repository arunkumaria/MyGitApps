package com.own.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemplateService {

	private final TemplateEngine templateEngine;

	/**
	 * Render a template with the provided data.
	 *
	 * @param templateName The name of the template (e.g., "welcome_email")
	 * @param variables    Map of variables to inject into the template
	 * @return The rendered HTML content as a String
	 */
	public String renderTemplate(String templateName, Map<String, Object> variables) {
		Context context = new Context();
		if (variables != null) {
			context.setVariables(variables);
		}
		return templateEngine.process(templateName, context);
	}
}