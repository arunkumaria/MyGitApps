package com.own.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RestaurantController {

    @GetMapping("/restaurants")
    public List<Map<String, Object>> restaurants() {

        return List.of(
                Map.of(
                        "id", 1,
                        "name", "Dominos"
                ),
                Map.of(
                        "id", 2,
                        "name", "KFC"
                )
        );
    }
}