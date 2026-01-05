package com.own.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.own.entity.RefreshToken;
import com.own.entity.User;
import com.own.repository.RefreshTokenRepository;
import com.own.repository.UserRepository;

@Service
@Transactional
public class RefreshTokenService {

	private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60; // 7 days

	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.userRepository = userRepository;
	}

	public RefreshToken createRefreshToken(Long userId) {

		// ✅ ALWAYS load managed entity
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// Optional: one refresh token per user
		refreshTokenRepository.deleteByUserId(userId);

		RefreshToken token = new RefreshToken();
		token.setUser(user);
		token.setToken(UUID.randomUUID().toString());
		token.setExpiryDate(Instant.now().plusSeconds(REFRESH_TOKEN_VALIDITY));

		return refreshTokenRepository.save(token);
	}

	public RefreshToken verifyExpiration(String token) {
		RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
				.orElseThrow(() -> new RuntimeException("Refresh token not found"));

		if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
			refreshTokenRepository.delete(refreshToken);
			throw new RuntimeException("Refresh token expired");
		}

		return refreshToken;
	}
}
