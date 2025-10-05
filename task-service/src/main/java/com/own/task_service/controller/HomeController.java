package com.own.task_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.own.task_service.service.*;

@RestController
public class HomeController {

	@GetMapping("/tasks")
	public ResponseEntity<String> HomeController() {

		return new ResponseEntity<>("Welcome to task Service", HttpStatus.OK);
	}
}