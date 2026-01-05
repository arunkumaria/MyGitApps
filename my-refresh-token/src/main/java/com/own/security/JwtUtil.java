package com.own.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	private final Key key = Keys.hmacShaKeyFor("my-super-secret-key-which-is-very-long".getBytes());

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}
}
