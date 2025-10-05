package com.own.basic_auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
    public String hello() {
        return "Welcome! You are successfully authenticated.";
    }
}
