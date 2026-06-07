package com.own.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	private static final Logger log = LoggerFactory.getLogger(HealthController.class);

	@GetMapping("/test")
	public String test() {

		log.info("Discovery service called");

		return "Discovery Running";
	}
}