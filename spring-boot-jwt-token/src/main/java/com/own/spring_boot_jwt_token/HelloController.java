package com.own.spring_boot_jwt_token;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "✅ Hello, you are authenticated!";
	}
}
