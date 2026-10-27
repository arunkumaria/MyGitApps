package com.finance.service;

import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.dto.AuthResponse;
import com.finance.dto.RegisterRequest;
import com.finance.entity.User;
import com.finance.repository.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public AuthResponse register(RegisterRequest request) {
		String email = request.email().trim().toLowerCase(Locale.ROOT);

		if (userRepository.existsByEmailIgnoreCase(email)) {
			throw new IllegalArgumentException("An account with this email already exists.");
		}

		User user = new User();
		user.setName(request.name().trim());
		user.setEmail(email);
		user.setPasswordHash(passwordEncoder.encode(request.password()));
		user.setRole("USER");

		User saved = userRepository.saveAndFlush(user);

		return new AuthResponse(saved.getId(), saved.getName(), saved.getEmail(),
				"Registration successful. Please sign in.");
	}
}
