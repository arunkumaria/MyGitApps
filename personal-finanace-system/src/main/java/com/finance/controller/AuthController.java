package com.finance.controller;

import com.finance.dto.AuthResponse;
import com.finance.dto.LoginRequest;
import com.finance.dto.RegisterRequest;
import com.finance.entity.User;
import com.finance.repository.UserRepository;
import com.finance.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.Locale;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;

	public AuthController(AuthService authService, AuthenticationManager authenticationManager,
			UserRepository userRepository) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
		try {
			AuthResponse response = authService.register(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {

		try {
			String email = request.email().trim().toLowerCase(Locale.ROOT);

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, request.password()));

			// Create a fresh session after authentication.
			httpRequest.getSession(true);

			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);

			new HttpSessionSecurityContextRepository().saveContext(context, httpRequest, httpResponse);

			User user = userRepository.findByEmailIgnoreCase(email).orElseThrow();

			return ResponseEntity
					.ok(new AuthResponse(user.getId(), user.getName(), user.getEmail(), "Login successful."));

		} catch (AuthenticationException ex) {
			SecurityContextHolder.clearContext();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid email or password."));
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> me(Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Please sign in."));
		}

		return userRepository.findByEmailIgnoreCase(authentication.getName())
				.<ResponseEntity<?>>map(user -> ResponseEntity
						.ok(new AuthResponse(user.getId(), user.getName(), user.getEmail(), "Authenticated")))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(Map.of("message", "User not found.")));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		SecurityContextHolder.clearContext();

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		response.setHeader("Clear-Site-Data", "\"cache\"");
		return ResponseEntity.ok(Map.of("message", "Logged out successfully."));
	}
}
