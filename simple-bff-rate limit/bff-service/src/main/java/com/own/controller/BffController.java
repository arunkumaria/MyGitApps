package com.own.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.client.FoodClient;
import com.own.client.RestaurantClient;

@RestController
public class BffController {

    private final RestaurantClient restaurantClient;
    private final FoodClient foodClient;

    public BffController(
            RestaurantClient restaurantClient,
            FoodClient foodClient
    ) {
        this.restaurantClient = restaurantClient;
        this.foodClient = foodClient;
    }
    @GetMapping(
            value = "/bff/dashboard",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, Object>> dashboard() {

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "restaurants",
                restaurantClient.getRestaurants()
        );

        response.put(
                "foods",
                foodClient.getFoods()
        );

        return ResponseEntity.ok(response);
    }
}