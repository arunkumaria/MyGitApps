package com.own.spring_boot_jwt.service;

import org.springframework.stereotype.Service;
import com.own.spring_boot_jwt.util.JwtUtil;

@Service
public class UserService {

	private final JwtUtil jwtUtil;

	public UserService(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	/**
	 * Dummy login for testing — replace with DB lookup
	 */
	public String login(String username, String password) {
		// Hardcoded credentials for demo
		if ("arun".equals(username) && "password123".equals(password)) {
			return jwtUtil.generateToken(username);
		}
		throw new RuntimeException("Invalid username or password");
	}

	/**
	 * Validates token and returns username
	 */
	public String validateAndGetUsername(String token) {
		if (jwtUtil.validateToken(token)) {
			return jwtUtil.extractUsername(token);
		}
		throw new RuntimeException("Invalid or expired token");
	}
}
