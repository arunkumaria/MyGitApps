package com.example.demo_azure.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo_azure.entity.Employee;
import com.example.demo_azure.repo.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

import java.lang.System.Logger;
import java.util.List;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {
	
	

	private final EmployeeRepository repository;

	public EmployeeController(EmployeeRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<Employee> getAll() {
		log.info("in controller class");
		return repository.findAll();
	}

	@PostMapping
	public Employee addEmployee(@RequestBody Employee emp) {
		return repository.save(emp);
	}
}
