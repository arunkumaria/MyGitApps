package com.own.demo_1.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.demo_1.entity.AuthRequest;
import com.own.demo_1.entity.UserInfo;
import com.own.demo_1.service.JwtService;
import com.own.demo_1.service.UserInfoService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserInfoService userInfoService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthController(UserInfoService userInfoService, AuthenticationManager authenticationManager,
			JwtService jwtService) {
		super();
		this.userInfoService = userInfoService;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@GetMapping
	public String auth() {
		return "you are not authenticated";
	}

	@PostMapping("/register")
	public UserInfo register(@RequestBody UserInfo userInfo) {
		return userInfoService.saveUser(userInfo);
	}

	@PostMapping("/login")
	public String login(@RequestBody AuthRequest authRequest) {
		System.out.println("Authenticating user: " + authRequest.getUsername());
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		UserDetails userDetails = userInfoService.loadUserByUsername(authRequest.getUsername());
		return jwtService.generateToken(userDetails);

	}

}
