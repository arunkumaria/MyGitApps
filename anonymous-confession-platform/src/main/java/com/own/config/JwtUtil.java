package com.own.config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.own.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 256-bit key

	public String generateToken(String username, Role role) {
		return Jwts.builder().setSubject(username).claim("role", role.name()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).signWith(key).compact();
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	public String extractRole(String token) {
		return (String) Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role");
	}

	public boolean validateToken(String token, String username) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return claims.getSubject().equals(username) && claims.getExpiration().after(new Date());
		} catch (JwtException e) {
			return false;
		}
	}
}