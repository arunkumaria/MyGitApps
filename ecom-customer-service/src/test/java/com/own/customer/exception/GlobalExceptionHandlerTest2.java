package com.own.customer.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

public class GlobalExceptionHandlerTest2 {

	private GlobalExceptionHandler globalExceptionHandler;

	@Mock
	private HttpServletRequest httpServletRequest;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		globalExceptionHandler = new GlobalExceptionHandler();
		when(httpServletRequest.getRequestURI()).thenReturn("/api/v1/customers");
	}

	@Test
	void handleCustomerNotFound_404() {
		CustomerNotFoundException customerNotFoundException = new CustomerNotFoundException(
				"customer not found with id: 123");
		ResponseEntity<ErrorResponse> response = globalExceptionHandler
				.handleCustomerNotFound(customerNotFoundException, httpServletRequest);

		assertNotNull(response);
		assertEquals(404, response.getStatusCode().value());
		ErrorResponse errorResponse = response.getBody();
		assertNotNull(errorResponse);
		assertEquals("customer not found with id: 123", errorResponse.getError());
		assertEquals("/api/v1/customers", errorResponse.getPath());
		assertNotNull(errorResponse.getTimestamp());
		assertEquals("Customer not found", errorResponse.getMessage());
		assertEquals(404, errorResponse.getStatus());
	}
}
