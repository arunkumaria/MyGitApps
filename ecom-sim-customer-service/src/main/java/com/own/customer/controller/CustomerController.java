package com.own.customer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.customer.model.Customer;
import com.own.customer.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@PostMapping
	public Customer createCustomer(@RequestBody Customer customer) {
		return service.createCustomer(customer);
	}
}
