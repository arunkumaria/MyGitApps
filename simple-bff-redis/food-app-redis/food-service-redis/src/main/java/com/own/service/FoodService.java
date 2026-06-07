package com.own.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.own.model.Food;

@Service
public class FoodService {

    @Cacheable(value = "foods")
    public List<Food> getFoods() {

        System.out.println("Fetching from DB");

        return List.of(
                new Food(1L, "Pizza", 250),
                new Food(2L, "Burger", 150),
                new Food(3L, "Pasta", 200)
        );
    }
}