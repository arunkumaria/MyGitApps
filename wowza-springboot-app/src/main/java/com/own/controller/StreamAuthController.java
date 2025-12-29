package com.own.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stream")
public class StreamAuthController {

	@GetMapping("/validate")
	public ResponseEntity<String> validate(@RequestParam String token, @RequestParam String streamName) {

		if ("valid123".equals(token)) {
			return ResponseEntity.ok("AUTHORIZED");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("DENIED");
	}
}
