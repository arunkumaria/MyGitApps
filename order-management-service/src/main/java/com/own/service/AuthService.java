package com.own.service;


import com.own.dto.AuthRequest;
import com.own.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    void register(AuthRequest request);
}
