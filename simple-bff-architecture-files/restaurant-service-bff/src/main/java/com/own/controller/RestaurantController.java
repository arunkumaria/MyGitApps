package com.own.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.Restaurant;
import com.own.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	private final RestaurantService service;

	public RestaurantController(RestaurantService service) {
		this.service = service;
	}

	@PostMapping
	public Restaurant create(@RequestBody Restaurant restaurant) {
		return service.save(restaurant);
	}

	@GetMapping
	public List<Restaurant> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Restaurant getById(@PathVariable Long id) {
		return service.getById(id);
	}
}