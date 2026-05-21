package com.own.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.Food;

@RestController
@RequestMapping("/foods")
public class FoodController {

    @GetMapping
    public List<Food> getFoods() {

        return List.of(
                new Food(1L, "Burger"),
                new Food(2L, "Pizza")
        );
    }
}