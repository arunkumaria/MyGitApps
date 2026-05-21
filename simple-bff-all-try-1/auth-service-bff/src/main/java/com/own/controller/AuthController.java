package com.own.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.LoginRequest;
import com.own.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {

        if ("admin".equals(request.getUsername())
                && "password".equals(request.getPassword())) {

            String token =
                    JwtUtil.generateToken(
                            request.getUsername()
                    );

            Map<String, String> response =
                    new HashMap<>();

            response.put("token", token);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(401)
                .body("Invalid Credentials");
    }
}