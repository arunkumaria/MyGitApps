package com.own.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.RestaurantResponse;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

	@GetMapping("/{id}")
	public RestaurantResponse getRestaurant(@PathVariable Long id) {

		RestaurantResponse response = new RestaurantResponse();

		response.setId(id);
		response.setName("Pizza Palace");
		response.setLocation("Bangalore");

		return response;
	}
}
