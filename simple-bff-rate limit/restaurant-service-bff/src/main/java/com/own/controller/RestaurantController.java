package com.own.controller;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

	private final Bucket restaurantBucket;

	@GetMapping
	public ResponseEntity<?> restaurants() {

		boolean allowed = restaurantBucket.tryConsume(1);

		System.out.println("Allowed = " + allowed);

		if (!allowed) {
			return ResponseEntity.status(429).body("Restaurant limit exceeded");
		}

		return ResponseEntity.ok(List.of("Dominos", "KFC", "Burger King"));
	}
}
