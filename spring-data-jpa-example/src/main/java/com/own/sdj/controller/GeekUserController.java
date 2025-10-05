package com.own.sdj.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.own.sdj.model.GeekUserRecord;
import com.own.sdj.service.GeekUserService;

import java.util.List;

@RestController
@RequestMapping("/api") // base path
public class GeekUserController {

	private final GeekUserService userService;

	public GeekUserController(GeekUserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/test")
	public String check() {
		return "verified";
	}

	// GET /api/users
	@GetMapping("/users")
	public List<GeekUserRecord> getAllUser() {
		return userService.getAllGeekUsers();
	}

	// POST /api/users
	@PostMapping("/users")
	public ResponseEntity<GeekUserRecord> addUser(@RequestBody GeekUserRecord userRecord) {
		GeekUserRecord saved = userService.addGeekUser(userRecord);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
}