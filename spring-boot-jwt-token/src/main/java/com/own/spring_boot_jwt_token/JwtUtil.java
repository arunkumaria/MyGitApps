package com.own.spring_boot_jwt_token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	// Must be at least 32 characters for HS256
	private static final String SECRET = "my-very-secure-and-long-secret-key-1234567890";
	private static final long EXPIRATION = 1000 * 60 * 60; // 1 hour

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}

	// Generate Token
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuer("jwt-demo").setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	// Extract Username
	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
	}

	// Validate Token
	public boolean validateToken(String token, String username) {
		try {
			String extracted = extractUsername(token);
			return extracted.equals(username) && !isTokenExpired(token);
		} catch (JwtException e) {
			// SignatureException, ExpiredJwtException, MalformedJwtException
			System.out.println("❌ Invalid JWT: " + e.getMessage());
			return false;
		}
	}

	private boolean isTokenExpired(String token) {
		Date exp = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody()
				.getExpiration();
		return exp.before(new Date());
	}
}
