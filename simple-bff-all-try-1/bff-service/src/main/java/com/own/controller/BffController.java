package com.own.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bff")
public class BffController {

    private final RestTemplate restTemplate =
            new RestTemplate();

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {

        System.out.println("Calling restaurant service");

        Object restaurants =
                restTemplate.getForObject(
                        "http://localhost:8082/restaurants",
                        Object.class
                );

        System.out.println("Calling food service");

        Object foods =
                restTemplate.getForObject(
                        "http://localhost:8083/foods",
                        Object.class
                );

        Map<String, Object> response =
                new HashMap<>();

        response.put("restaurants", restaurants);
        response.put("foods", foods);

        return response;
    }
}