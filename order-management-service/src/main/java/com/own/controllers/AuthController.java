package com.own.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.AuthRequest;
import com.own.dto.AuthResponse;
import com.own.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Authentication APIs")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public String register(@RequestBody AuthRequest request) {
		authService.register(request);
		return "User registered successfully";
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest request) {
		return authService.login(request);
	}
}
