package com.own.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "food-service")
public interface FoodClient {

	@GetMapping("/foods")
	List<Map<String, Object>> getFoods();
}