package com.own.customer.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.customer.dto.CustomerRequest;
import com.own.customer.dto.CustomerResponse;
import com.own.customer.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping
	public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
		CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
	}

	@GetMapping
	public ResponseEntity<CustomerResponse> getCustomer(@PathVariable UUID id) {
		return ResponseEntity.ok(customerService.getCustomer(id));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Page<CustomerResponse>> getCustomers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(defaultValue = "DESC") String direction) {

		Sort.Direction sortDirection = Sort.Direction.fromString(direction);

		Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);

		return ResponseEntity.ok(customerService.getCustomers(pageable));

	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable UUID id,
			@Valid @RequestBody CustomerRequest customerRequest) {
		return ResponseEntity.ok(customerService.updateCustomer(id, customerRequest));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {

		customerService.deleteCustomer(id);
		return ResponseEntity.noContent().build();

	}

}
