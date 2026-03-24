package com.own.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class ShopifyService {

    private final WebClient webClient;

    public ShopifyService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String executeGraphQL(String shop, String token, String query) {

        return webClient.post()
                .uri("https://" + shop + "/admin/api/2024-01/graphql.json")
                .header("X-Shopify-Access-Token", token)
                .bodyValue(Map.of("query", query))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}