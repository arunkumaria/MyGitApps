package com.own.springboot_crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	// Request body class for login
	public static class AuthRequest {
		public String username;
		public String password;

		// getters and setters
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	// Response class for token
	public static class AuthResponse {
		private final String jwt;

		public AuthResponse(String jwt) {
			this.jwt = jwt;
		}

		public String getJwt() {
			return jwt;
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		if (userService.authenticate(request.getUsername(), request.getPassword())) {
			String token = jwtUtil.generateToken(request.getUsername());
			return ResponseEntity.ok(new AuthResponse(token));
		} else {
			return ResponseEntity.status(401).body("Invalid Credentials");
		}
	}
}
