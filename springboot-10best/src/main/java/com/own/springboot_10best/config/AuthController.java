package com.own.springboot_10best.config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

record LoginRequest(String username,String password){}record TokenResponse(String token){}

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public AuthController(AuthenticationManager am, JwtUtil jwtUtil) {
		this.authenticationManager = am;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
		String token = jwtUtil.generateToken(auth.getName());
		return ResponseEntity.ok(new TokenResponse(token));
	}
}
