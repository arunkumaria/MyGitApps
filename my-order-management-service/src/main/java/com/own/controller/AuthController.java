package com.own.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.AuthRequest;
import com.own.model.User;
import com.own.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final UserServiceImpl userServiceImpl;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
		if (userServiceImpl.registerService(authRequest) != null) {
			return ResponseEntity.ok("user registered successfully");
		} else {
			return ResponseEntity.ok("user registration failed");
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

		return ResponseEntity.ok(userServiceImpl.loginService(authRequest));
	}

}
