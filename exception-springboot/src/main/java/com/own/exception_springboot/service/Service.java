package com.own.exception_springboot.service;

import org.springframework.http.ResponseEntity;

import com.own.exception_springboot.exception.EmployeeNotFoundException;

@org.springframework.stereotype.Service
public class Service {
	
	public ResponseEntity<EmployeeNotFoundException> getEmployeeId(int id) {
		throw new EmployeeNotFoundException("invalid");
	}

}
