package com.own.logback_springboot.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	// Create a logger for this class
	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

	@GetMapping("/hello")
	public String hello() {
		logger.info("INFO log: Hello endpoint was called");
		logger.debug("DEBUG log: Useful for debugging purposes");
		logger.error("ERROR log: Something went wrong (sample log)");

		return "Hello, check your console for logs!";
	}
}
