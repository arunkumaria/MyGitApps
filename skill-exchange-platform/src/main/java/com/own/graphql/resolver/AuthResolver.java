package com.own.graphql.resolver;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.own.application.service.AuthService;
import com.own.domain.model.AuthResponse;

@Controller
public class AuthResolver {

    private final AuthService authService;

    public AuthResolver(AuthService authService) {
        this.authService = authService;
    }

    @MutationMapping
    public AuthResponse register(@Argument String email, @Argument String password) {
        return authService.register(email, password);
    }

    @MutationMapping
    public AuthResponse login(@Argument String email, @Argument String password) {
        return authService.login(email, password);
    }
}