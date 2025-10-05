package com.own.spring_spring_exception.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks the class as a RESTful controller
public class UserController {

	// Endpoint to fetch user by ID
	@GetMapping("/user/{id}")
	public String getUserById(@PathVariable int id) {
		// If the ID is negative, throw an IllegalArgumentException
		if (id < 0) {
			throw new IllegalArgumentException("Invalid user ID: " + id);
		}
		// Return user information if ID is valid
		return "User with ID: " + id;
	}
}
