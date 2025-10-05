package com.own.demo_1.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.own.demo_1.entity.UserInfo;
import com.own.demo_1.repo.UserInfoRepo;
import com.own.jwt_springboot.repo.UserInfoRepository;

@Service
public class UserInfoService implements UserDetailsService {

	private final UserInfoRepo userInfoRepo;
	private final PasswordEncoder passwordEncoder;

	public UserInfoService(UserInfoRepo userInfoRepo, PasswordEncoder passwordEncoder) {
		this.userInfoRepo = userInfoRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userInfoRepo.findByName(username)
				.map(obj -> User.builder().username(obj.getName()).password(obj.getPassword())
						.roles(obj.getRoles().split(",")).build())
				.orElseThrow(() -> new UsernameNotFoundException("user not found" + username));
	}

	public UserInfo saveUser(UserInfo userInfo) {
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		return userInfoRepo.save(userInfo);

	}
}
