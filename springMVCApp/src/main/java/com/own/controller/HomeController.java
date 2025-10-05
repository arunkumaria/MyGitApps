package com.own.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "index"; // loads index.jsp
	}

	@GetMapping("/greet")
	public String greet(Model model) {
		model.addAttribute("result", "Welcome to Spring MVC with Java Config!");
		return "index";
	}
}
