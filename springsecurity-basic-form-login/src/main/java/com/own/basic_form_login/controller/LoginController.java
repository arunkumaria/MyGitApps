package com.own.basic_form_login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/welcome")
	public String welcome() {
		return "welcome"; // Thymeleaf template name
	}

	@GetMapping("/login")
	public String login() {
		return "login"; // Thymeleaf template name
	}
}