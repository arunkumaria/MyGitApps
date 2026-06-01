package com.own.controller;


import com.own.client.FoodClient;
import com.own.client.RestaurantClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bff")
public class BffController {

    private final FoodClient foodClient;

    private final RestaurantClient restaurantClient;

    public BffController(
            FoodClient foodClient,
            RestaurantClient restaurantClient
    ) {

        this.foodClient = foodClient;
        this.restaurantClient = restaurantClient;
    }

    @GetMapping("/dashboard")

    @CircuitBreaker(
            name = "foodService",
            fallbackMethod = "fallbackDashboard"
    )
    public Map<String, Object> dashboard() {

        return Map.of(
                "foods", foodClient.foods(),
                "restaurants", restaurantClient.restaurants()
        );
    }

    public Map<String, Object> fallbackDashboard(
            Exception ex
    ) {

        return Map.of(
                "message",
                "Fallback Response"
        );
    }
}