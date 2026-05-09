package com.own.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NotificationController {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@PostMapping("/notify")
	public String sendNotification(@RequestBody String message) {

		redisTemplate.convertAndSend("notifications", message);

		return "Notification sent";
	}

	@GetMapping("/health")
	public String health() {

		return "OK";

	}
}
