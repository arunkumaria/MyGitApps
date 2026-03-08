package com.own.domain.model;


public class AuthResponse {

    private String token;
    private String email;
    private String message;

    public AuthResponse(String token, String email, String message) {
        this.token = token;
        this.email = email;
        this.message = message;
    }

    // GraphQL serialization needs getters
    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}