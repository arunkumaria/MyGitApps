package com.own.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRoutes(
            RouteLocatorBuilder builder
    ) {

        return builder.routes()

                .route("bff-service", route -> route
                        .path("/api/bff/**")
                        .filters(filter -> filter
                                .rewritePath(
                                        "/api/(?<segment>.*)",
                                        "/${segment}"
                                )
                        )
                        .uri("http://localhost:8080")
                )

                .route("order-service", route -> route
                        .path("/api/orders/**")
                        .uri("http://localhost:8081")
                )

                .route("restaurant-service", route -> route
                        .path("/api/restaurants/**")
                        .uri("http://localhost:8083")
                )

                .route("food-service", route -> route
                        .path("/api/foods/**")
                        .uri("http://localhost:8084")
                )

                .build();
    }
}