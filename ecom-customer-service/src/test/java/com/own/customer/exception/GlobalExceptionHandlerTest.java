package com.own.customer.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.servlet.http.HttpServletRequest;

class GlobalExceptionHandlerTest {

	private GlobalExceptionHandler globalExceptionHandler;

	@Mock
	private HttpServletRequest httpServletRequest;

	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		globalExceptionHandler = new GlobalExceptionHandler();

		when(httpServletRequest.getRequestURI()).thenReturn("/api/v1/customers");
	}

	// ---------------------------------------------------------
	// CustomerNotFoundException
	// ---------------------------------------------------------

	@Test
	void handleCustomerNotFound_shouldReturn404() {

		CustomerNotFoundException exception = new CustomerNotFoundException("Customer not found with id: 123");

		ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleCustomerNotFound(exception,
				httpServletRequest);

		assertNotNull(response);

		assertEquals(404, response.getStatusCode().value());

		ErrorResponse errorResponse = response.getBody();

		assertNotNull(errorResponse);

		assertEquals(404, errorResponse.getStatus());

		// Constructor parameter 3 -> message
		assertEquals("Customer not found", errorResponse.getMessage());

		// Constructor parameter 4 -> error
		assertEquals("Customer not found with id: 123", errorResponse.getError());

		assertEquals("/api/v1/customers", errorResponse.getPath());

		assertNotNull(errorResponse.getTimestamp());
	}

	// ---------------------------------------------------------
	// IllegalArgumentException
	// ---------------------------------------------------------

	@Test
	void handleNotFound_shouldReturn400() {

		IllegalArgumentException exception = new IllegalArgumentException("Customer email already exists");

		ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNotFound(exception, httpServletRequest);

		assertNotNull(response);

		assertEquals(400, response.getStatusCode().value());

		ErrorResponse errorResponse = response.getBody();

		assertNotNull(errorResponse);

		assertEquals(400, errorResponse.getStatus());

		// "Bad Request" is passed as message
		assertEquals("Bad Request", errorResponse.getMessage());

		// Exception message is passed as error
		assertEquals("Customer email already exists", errorResponse.getError());

		assertEquals("/api/v1/customers", errorResponse.getPath());

		assertNotNull(errorResponse.getTimestamp());
	}

	// ---------------------------------------------------------
	// MethodArgumentNotValidException
	// ---------------------------------------------------------

	@Test
	void handleValidation_shouldReturn400() {

		BindingResult bindingResult = mock(BindingResult.class);

		FieldError nameError = new FieldError("customerRequest", "name", "Name is required");

		FieldError emailError = new FieldError("customerRequest", "email", "Email must be valid");

		when(bindingResult.getFieldErrors()).thenReturn(List.of(nameError, emailError));

		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

		ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidation(exception, httpServletRequest);

		assertNotNull(response);

		assertEquals(400, response.getStatusCode().value());

		ErrorResponse errorResponse = response.getBody();

		assertNotNull(errorResponse);

		assertEquals(400, errorResponse.getStatus());

		// "Validation failed" is passed as message
		assertEquals("Validation failed", errorResponse.getMessage());

		// Validation details are passed as error
		assertEquals("name: Name is required,email: Email must be valid", errorResponse.getError());

		assertEquals("/api/v1/customers", errorResponse.getPath());

		assertNotNull(errorResponse.getTimestamp());
	}

	// ---------------------------------------------------------
	// Generic Exception
	// ---------------------------------------------------------

	@Test
	void handleGenericException_shouldReturn500() {

		Exception exception = new Exception("Unexpected database error");

		ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(exception,
				httpServletRequest);

		assertNotNull(response);

		assertEquals(500, response.getStatusCode().value());

		ErrorResponse errorResponse = response.getBody();

		assertNotNull(errorResponse);

		assertEquals(500, errorResponse.getStatus());

		// "Internal Server Error" is passed as message
		assertEquals("Internal Server Error", errorResponse.getMessage());

		// Exception message is passed as error
		assertEquals("Unexpected database error", errorResponse.getError());

		assertEquals("/api/v1/customers", errorResponse.getPath());

		assertNotNull(errorResponse.getTimestamp());
	}
}
