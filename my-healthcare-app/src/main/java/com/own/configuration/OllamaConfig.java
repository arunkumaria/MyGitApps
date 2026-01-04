package com.own.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.Duration;

@Configuration
public class OllamaConfig {

	@Value("${ollama.base-url}")
	private String ollamaBaseUrl;

	@Value("${ollama.timeout}")
	private long timeout;

	@Bean
	public RestTemplate ollamaRestTemplate(RestTemplateBuilder builder) {
		return builder.rootUri(ollamaBaseUrl).setConnectTimeout(Duration.ofMillis(timeout))
				.setReadTimeout(Duration.ofMillis(timeout)).build();
	}
}
