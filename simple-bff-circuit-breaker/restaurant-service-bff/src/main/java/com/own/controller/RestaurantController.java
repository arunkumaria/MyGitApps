package com.own.controller;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RestaurantController {

    @GetMapping("/restaurants")
    public ResponseEntity<?> restaurants() {

        return ResponseEntity.ok(

                List.of(

                        Map.of(
                                "id", 1,
                                "name", "Dominos"
                        ),

                        Map.of(
                                "id", 2,
                                "name", "KFC"
                        )
                )
        );
    }
}