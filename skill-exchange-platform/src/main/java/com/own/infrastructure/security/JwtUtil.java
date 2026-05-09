package com.own.infrastructure.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	// Secret key (must be >= 32 characters for HS256)
	private final String SECRET_KEY = "skill-exchange-secret-key-skill-exchange";

	// Create key object
	private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	// Token validity (24 hours)
	private final long JWT_EXPIRATION = 24 * 60 * 60 * 1000;

	// 1️⃣ Generate token
	public String generateToken(String email) {
		return Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	// 2️⃣ Extract email
	public String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// 3️⃣ Extract expiration
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// 4️⃣ Extract claim
	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		final Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}

	// 5️⃣ Validate token
	public boolean validateToken(String token, UserDetails userDetails) {
		final String email = extractEmail(token);
		return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	// 6️⃣ Check expiration
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// 7️⃣ Parse token
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}