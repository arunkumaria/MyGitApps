//package com.own.util;
//
//import java.nio.charset.StandardCharsets;
//
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//
//@Component
//public class JwtUtil {
//
//    private static final String SECRET = "mysecretkeymysecretkeymysecretkey123456";
//
//    public boolean validateToken(String token) {
//
//        try {
//
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(
//                            Keys.hmacShaKeyFor(
//                                    SECRET.getBytes(StandardCharsets.UTF_8)
//                            )
//                    )
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            return claims.getSubject() != null;
//
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
