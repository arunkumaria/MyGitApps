package com.own.spring_boot_jwt.controller;

import org.springframework.web.bind.annotation.*;

import com.own.spring_boot_jwt.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final JwtUtil jwtUtil;

	public AuthController(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public String login(@RequestParam String username) {
		// Normally you would validate username/password from DB
		return jwtUtil.generateToken(username);
	}
}
