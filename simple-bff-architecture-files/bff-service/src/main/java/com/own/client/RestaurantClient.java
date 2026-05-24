package com.own.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.own.dto.RestaurantDTO;

@FeignClient(name = "restaurant-service", url = "http://localhost:8081")
public interface RestaurantClient {

	@GetMapping("/restaurants/{id}")
	RestaurantDTO getRestaurant(@PathVariable Long id);
}
