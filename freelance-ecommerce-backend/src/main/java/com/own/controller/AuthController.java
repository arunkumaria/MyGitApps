package com.own.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.entity.User;
import com.own.repository.UserRepository;
import com.own.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepository repo;
    @Autowired private JwtUtil jwt;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User dbUser = repo.findByUsername(user.getUsername()).orElseThrow();
        return jwt.generateToken(dbUser.getUsername());
    }
}
