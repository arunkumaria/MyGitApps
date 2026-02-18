package com.own.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.own.dto.AuthRequest;
import com.own.model.User;
import com.own.repository.UserRepository;
import com.own.service.interfaces.UserServiceInterface;
import com.own.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserServiceInterface {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public User registerService(AuthRequest authRequest) {

		User temp = User.builder().username(authRequest.getUsername())
				.password(bCryptPasswordEncoder.encode(authRequest.getPassword())).mobile(authRequest.getMobile())
				.salary(authRequest.getSalary()).build();

		return userRepository.save(temp);
	}

	public String loginService(AuthRequest authRequest) {
		User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(() -> new RuntimeException());
		log.info("user:" + user);

		if (user != null && (user.getUsername().equals(authRequest.getUsername())
				&& bCryptPasswordEncoder.matches(authRequest.getPassword(), user.getPassword()))) {
			return jwtUtil.generateToken(authRequest.getUsername());
		}

		return "token can't be generated";

	}

}
