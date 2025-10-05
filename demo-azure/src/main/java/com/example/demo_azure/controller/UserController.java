package com.example.demo_azure.controller;

import com.example.demo_azure.entity.*;
import com.example.demo_azure.repo.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserRepository repo;

	public UserController(UserRepository repo) {
		this.repo = repo;
	}

	@PostMapping
	public User addUser(@RequestBody User user) {
		return repo.save(user);
	}

	@GetMapping
	public List<User> getUsers() {
		return repo.findAll();
	}
	
	@GetMapping
	public String hello() {
		return "hello azure";
	}
}
