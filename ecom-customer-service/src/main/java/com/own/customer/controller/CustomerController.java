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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer Management", description = "APIs for creating, retrieving, updating and deleting customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Operation(summary = "Create a new customer", description = "Creates a new customer and publishes a customer-created Kafka event.")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Customer created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid customer data", content = @Content),
			@ApiResponse(responseCode = "409", description = "Customer email already exists", content = @Content) })
	@PostMapping
	public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {

		CustomerResponse response = customerService.createCustomer(customerRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "Get customer by ID", description = "Retrieves a customer by its UUID. "
			+ "The response may be served from Redis cache.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Customer found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
			@ApiResponse(responseCode = "404", description = "Customer not found", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponse> getCustomer(

			@Parameter(description = "Customer UUID", required = true, example = "682cf0b9-9064-4d8d-86f4-2ec161a423a6") @PathVariable UUID id) {

		return ResponseEntity.ok(customerService.getCustomer(id));
	}

	@Operation(summary = "Get customers", description = "Returns a paginated list of customers.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Customers retrieved successfully") })
	@GetMapping
	public ResponseEntity<Page<CustomerResponse>> getCustomers(

			@Parameter(description = "Page number (zero based)", example = "0") @RequestParam(defaultValue = "0") int page,

			@Parameter(description = "Number of customers per page", example = "10") @RequestParam(defaultValue = "10") int size,

			@Parameter(description = "Field used for sorting", example = "createdAt") @RequestParam(defaultValue = "createdAt") String sortBy,

			@Parameter(description = "Sort direction", example = "DESC") @RequestParam(defaultValue = "DESC") String direction) {

		Sort.Direction sortDirection = Sort.Direction.fromString(direction);

		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

		return ResponseEntity.ok(customerService.getCustomers(pageable));
	}

	@Operation(summary = "Update customer", description = "Updates an existing customer and publishes a customer-updated Kafka event.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Customer updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid customer data", content = @Content),
			@ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
			@ApiResponse(responseCode = "409", description = "Email already belongs to another customer", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<CustomerResponse> updateCustomer(

			@Parameter(description = "Customer UUID", required = true, example = "682cf0b9-9064-4d8d-86f4-2ec161a423a6") @PathVariable UUID id,

			@Valid @RequestBody CustomerRequest customerRequest) {

		return ResponseEntity.ok(customerService.updateCustomer(id, customerRequest));
	}

	@Operation(summary = "Delete customer", description = "Deletes a customer by UUID and removes the customer from Redis cache.")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Customer not found", content = @Content) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(

			@Parameter(description = "Customer UUID", required = true, example = "682cf0b9-9064-4d8d-86f4-2ec161a423a6") @PathVariable UUID id) {

		customerService.deleteCustomer(id);

		return ResponseEntity.noContent().build();
	}
}