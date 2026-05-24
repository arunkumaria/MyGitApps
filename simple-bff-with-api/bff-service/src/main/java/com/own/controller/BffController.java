package com.own.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.OrderResponse;
import com.own.dto.RestaurantResponse;
import com.own.service.BffService;

@RestController
@RequestMapping("/bff")
public class BffController {

	private final BffService bffService;

	public BffController(BffService bffService) {
		this.bffService = bffService;
	}

	@GetMapping("/orders/{id}")
	public OrderResponse getOrder(@PathVariable Long id) {
		return bffService.getOrder(id);
	}

	@GetMapping("/restaurant/{id}")
	public RestaurantResponse getRestaurant(@PathVariable Long id) {
		return bffService.getRestaurant(id);
	}

	@GetMapping("/foods")
	public List getFoods() {
		return bffService.getFoods();
	}
}
