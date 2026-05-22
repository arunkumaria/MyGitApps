package com.own.controller;



import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodController {

    @GetMapping("/foods")
    public List<Map<String, Object>> foods() {

        return List.of(
                Map.of(
                        "id", 1,
                        "name", "Burger"
                ),
                Map.of(
                        "id", 2,
                        "name", "Pizza"
                )
        );
    }
}