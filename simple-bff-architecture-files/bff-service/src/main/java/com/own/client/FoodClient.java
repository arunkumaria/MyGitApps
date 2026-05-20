package com.own.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.own.dto.FoodDTO;

@FeignClient(name = "food-service", url = "http://localhost:8083")
public interface FoodClient {

	@GetMapping("/foods/restaurant/{restaurantId}")
	List<FoodDTO> getFoods(@PathVariable Long restaurantId);
}
