package com.own.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.own.client.FoodClient;
import com.own.client.RestaurantClient;

@Service
public class BffService {

    private final FoodClient foodClient;
    private final RestaurantClient restaurantClient;

    public BffService(FoodClient foodClient,
                      RestaurantClient restaurantClient) {

        this.foodClient = foodClient;
        this.restaurantClient = restaurantClient;
    }

    @Cacheable("dashboard")
    public Map<String, Object> dashboard() {

        System.out.println("Calling Microservices");

        Map<String, Object> map = new HashMap<>();

        map.put("foods", foodClient.foods());
        map.put("restaurants", restaurantClient.restaurants());

        return map;
    }
}