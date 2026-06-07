package com.own.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private static final Logger log =
            LoggerFactory.getLogger(RestaurantController.class);

    @GetMapping
    public List<String> restaurants() {

        log.info("Fetching Restaurants");

        return List.of(
                "KFC",
                "McDonalds",
                "Dominos"
        );
    }
}