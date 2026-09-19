package com.own.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customerServiceOpenAPI() {

		return new OpenAPI().info(new Info().title("E-Commerce Customer Service API")
				.description("REST API for managing customers in the " + "AI-driven marketing platform. "
						+ "The service supports customer CRUD operations, " + "Redis caching, Kafka events and "
						+ "Resilience4j-based downstream protection.")
				.version("1.0.0").contact(new Contact().name("Customer Service Team"))
				.license(new License().name("Internal Use")));
	}
}
