package com.own.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.own.entity.RefreshToken;
import com.own.entity.User;
import com.own.repository.UserRepository;
import com.own.service.RefreshTokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserRepository userRepository;
	private final RefreshTokenService refreshTokenService;

	public AuthController(UserRepository userRepository, RefreshTokenService refreshTokenService) {
		this.userRepository = userRepository;
		this.refreshTokenService = refreshTokenService;
	}

	// SIGNUP
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		return ResponseEntity.ok(userRepository.save(user));
	}

	// LOGIN (mocked – password check skipped for simplicity)
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username) {

		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

		return ResponseEntity.ok(refreshToken.getToken());
	}

	// REFRESH
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestParam String refreshToken) {

		RefreshToken token = refreshTokenService.verifyExpiration(refreshToken);

		return ResponseEntity.ok("New access token for user: " + token.getUser().getUsername());
	}
}
