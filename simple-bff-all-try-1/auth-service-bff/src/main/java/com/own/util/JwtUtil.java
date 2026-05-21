package com.own.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

	private static final String SECRET = "mysecretkeymysecretkeymysecretkey";

	private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

	private static final long EXPIRATION = 1000 * 60 * 60;

	public static String generateToken(String username) {

		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)).signWith(KEY).compact();
	}

	public static boolean validateToken(String token) {

		try {

			Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);

			return true;

		} catch (Exception e) {

			return false;
		}
	}

	public static String extractUsername(String token) {

		Claims claims = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody();

		return claims.getSubject();
	}
}