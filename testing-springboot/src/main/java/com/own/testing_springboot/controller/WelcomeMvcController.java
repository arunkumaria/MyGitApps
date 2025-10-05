package com.own.testing_springboot.controller;

import com.own.testing_springboot.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeMvcController {

	private final WelcomeService welcomeService;

	public WelcomeMvcController(WelcomeService welcomeService) {
		this.welcomeService = welcomeService;
	}

	@GetMapping("/")
	public String greeting1(String name, Model model) {
		model.addAttribute("welcome", welcomeService.greetingMessage1(name));
		return "welcome-page";
	}

	@GetMapping("/event")
	public String greeting2(String name, Model model) {
		model.addAttribute("welcomeToEvent", welcomeService.greetingMessage2(name));
		return "event-page";
	}
}