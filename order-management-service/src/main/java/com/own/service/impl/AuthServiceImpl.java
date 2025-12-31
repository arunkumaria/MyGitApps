package com.own.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.own.dto.AuthRequest;
import com.own.dto.AuthResponse;
import com.own.entity.User;
import com.own.exception.UnauthorizedException;
import com.own.repository.UserRepository;
import com.own.security.JwtUtil;
import com.own.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final BCryptPasswordEncoder encoder;

	@Override
	public AuthResponse login(AuthRequest request) {
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

		if (!encoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		return new AuthResponse(jwtUtil.generateToken(user.getUsername()));
	}

	@Override
	public void register(AuthRequest request) {
		User user = User.builder().username(request.getUsername()).password(encoder.encode(request.getPassword()))
				.role("ROLE_USER").build();
		userRepository.save(user);
	}
}
