package com.own.exception_springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.own.exception_springboot.exception.EmployeeNotFoundException;
import com.own.exception_springboot.model.Employee;
import com.own.exception_springboot.service.Service;

@RestController
public class Controller {

	@Autowired
	Service employeeService;

	@GetMapping("/{id}")
	public ResponseEntity<ResponseEntity<EmployeeNotFoundException>> getEmployee(@PathVariable("id") int id) {
		return ResponseEntity.ok(employeeService.getEmployeeId(id));
	}
}
