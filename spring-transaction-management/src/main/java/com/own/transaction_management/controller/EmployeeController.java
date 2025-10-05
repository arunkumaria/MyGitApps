package com.own.transaction_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.own.transaction_management.model.Employee;
import com.own.transaction_management.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/add")
	public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
		try {
			Employee employeeSaved = employeeService.addEmployee(employee);
			return ResponseEntity.ok(employeeSaved);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Transaction failed: " + e.getMessage());
		}
	}
}
