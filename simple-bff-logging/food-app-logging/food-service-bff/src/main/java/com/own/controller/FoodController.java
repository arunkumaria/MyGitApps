package com.own.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
public class FoodController {

    private static final Logger log =
            LoggerFactory.getLogger(FoodController.class);

    @GetMapping
    public List<String> foods() {

        log.info("Fetching Foods");

        return List.of(
                "Pizza",
                "Burger",
                "Pasta"
        );
    }
}