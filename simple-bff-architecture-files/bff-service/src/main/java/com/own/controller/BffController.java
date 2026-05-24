package com.own.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.AggregatedResponse;
import com.own.service.BffService;

@RestController
@RequestMapping("/bff")
public class BffController {

	private final BffService service;

	public BffController(BffService service) {
		this.service = service;
	}

	@GetMapping("/restaurant/{id}")
	public AggregatedResponse getRestaurant(@PathVariable Long id) {

		return service.getRestaurantDetails(id);
	}
}
