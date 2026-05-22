package com.own.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BffController {

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping(value = "/bff/dashboard", produces = "application/json")
	public ResponseEntity<Map<String, Object>> dashboard() {

		List restaurants = restTemplate.getForObject("http://localhost:8082/restaurants", List.class);

		List foods = restTemplate.getForObject("http://localhost:8083/foods", List.class);

		Map<String, Object> response = new HashMap<>();

		response.put("restaurants", restaurants);
		response.put("foods", foods);

		return ResponseEntity.ok(response);
	}
}