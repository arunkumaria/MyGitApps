package com.own.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.User;
import com.own.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		if (userService.registerService(user) != null) {
			return ResponseEntity.ok("user registered successfully");
		} else {
			return ResponseEntity.ok("user registration failed");
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password) {

		log.info("username"+username);
		return ResponseEntity.ok(userService.loginService(username, password));
	}

}
