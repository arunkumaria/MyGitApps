package com.own.spring_security_method_level.controller;


import com.own.spring_security_method_level.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userAccess() {
        return userService.getUserData();
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return userService.getAdminData();
    }
}
