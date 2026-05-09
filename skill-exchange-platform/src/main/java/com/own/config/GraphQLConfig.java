package com.own.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

	// Optional: register custom wiring for enums or scalars
	public RuntimeWiringConfigurer runtimeWiringConfigurer() {
		return wiringBuilder -> {
			// Example: wiringBuilder.type("MyEnum", typeWiring ->
			// typeWiring.enumValues(...));
		};
	}
}
