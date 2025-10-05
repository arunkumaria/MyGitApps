package com.own.jwt_springboot.controller;

import com.own.jwt_springboot.entity.UserInfo;
import com.own.jwt_springboot.service.JwtService;
import com.own.jwt_springboot.service.UserInfoService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserInfoService userInfoService;

	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
			UserInfoService userInfoService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userInfoService = userInfoService;
	}
	
	@GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

	@PostMapping("/register")
	public UserInfo register(@RequestBody UserInfo userInfo) {
		return userInfoService.saveUser(userInfo);
	}

	@PostMapping("/login")
	public String login(@RequestBody AuthRequest request) {
		try {
			System.out.println("Authenticating user: " + request.getUsername());
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid credentials");
		}

		final UserDetails userDetails = userInfoService.loadUserByUsername(request.getUsername());
		return jwtService.generateToken(userDetails);
	}

	@Data
	public static class AuthRequest {
		private String username;
		private String password;

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
}

