package com.own.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.Food;
import com.own.service.FoodService;

@RestController
@RequestMapping("/foods")
public class FoodController {

	private final FoodService service;

	public FoodController(FoodService service) {
		this.service = service;
	}

	@PostMapping
	public Food create(@RequestBody Food food) {
		return service.create(food);
	}

	@GetMapping("/restaurant/{restaurantId}")
	public List<Food> getFoods(@PathVariable Long restaurantId) {

		return service.getFoods(restaurantId);
	}
}