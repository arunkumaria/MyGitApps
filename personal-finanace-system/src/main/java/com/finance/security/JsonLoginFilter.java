package com.finance.security;


import java.io.IOException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class JsonLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            LoginRequest body = mapper.readValue(
                request.getInputStream(), LoginRequest.class);

            if (body.email() == null || body.password() == null) {
                throw new AuthenticationServiceException(
                    "Email and password are required");
            }

            var token = UsernamePasswordAuthenticationToken.unauthenticated(
                body.email().trim().toLowerCase(),
                body.password()
            );

            return authenticationManager.authenticate(token);

        } catch (IOException exception) {
            throw new AuthenticationServiceException(
                "Invalid login request", exception);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            jakarta.servlet.FilterChain chain,
            Authentication auth) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(),
            Map.of("email", auth.getName(), "authenticated", true));
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(),
            Map.of("error", "Invalid email or password"));
    }

    public record LoginRequest(String email, String password) {}
}
