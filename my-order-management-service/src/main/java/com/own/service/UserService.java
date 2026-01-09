package com.own.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.own.model.User;
import com.own.repository.UserRepository;
import com.own.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public User registerService(User user) {

		User temp = new User();
		temp.setUsername(user.getUsername());
		temp.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		temp.setMobile(user.getMobile());
		temp.setSalary(user.getSalary());

		return userRepository.save(temp);
	}

	public String loginService(String username, String password) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException());
		log.info("user:" + user);

		if (user != null && (user.getUsername().equals(username)
				&& bCryptPasswordEncoder.matches(password, user.getPassword()))) {
			return jwtUtil.generateToken(username);
		}

		return "token can't be generated";

	}

}
