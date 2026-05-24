package com.own.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

	@GetMapping
	public List<String> getFoods() {

		return List.of("Burger", "Pizza", "Pasta");
	}
}
