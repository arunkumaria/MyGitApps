package com.own.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.client.RestaurantClient;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BffController {

	private final RestaurantClient restaurantClient;
	private final Bucket bucket;

	@GetMapping("/restaurants")
	public ResponseEntity<?> restaurants() {

		if (!bucket.tryConsume(1)) {
			return ResponseEntity.status(429).body("Rate limit exceeded at BFF");
		}

		return ResponseEntity.ok(restaurantClient.restaurants());
	}
}