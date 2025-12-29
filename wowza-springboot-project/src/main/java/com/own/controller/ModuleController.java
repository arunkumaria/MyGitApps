package com.own.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

	@GetMapping("/status")
	public ResponseEntity<Map<String, Object>> getStatus() {
		Map<String, Object> status = new HashMap<>();
		status.put("status", "running");
		status.put("modules", Arrays.asList("ModuleConnectionLogger", "ModulePublishAuth"));
		status.put("timestamp", new Date());
		return ResponseEntity.ok(status);
	}

	@GetMapping("/info")
	public ResponseEntity<Map<String, String>> getInfo() {
		Map<String, String> info = new HashMap<>();
		info.put("version", "1.0.0");
		info.put("description", "Wowza Spring Boot Module Builder");
		info.put("wowzaVersion", "4.8.0");
		return ResponseEntity.ok(info);
	}

	@PostMapping("/reload")
	public ResponseEntity<Map<String, String>> reloadModules() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Module reload requested");
		response.put("status", "success");
		return ResponseEntity.ok(response);
	}
}
