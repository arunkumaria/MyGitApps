package com.own.customer.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(CustomerNotFoundException customerNotFoundException,
			HttpServletRequest httpServletRequest) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				"Customer not found", customerNotFoundException.getMessage(), httpServletRequest.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(IllegalArgumentException illegalArgumentException,
			HttpServletRequest httpServletRequest) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				"Bad Request", illegalArgumentException.getMessage(), httpServletRequest.getRequestURI());

		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(
			MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest httpServletRequest) {
		String message = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.joining(","));

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				"Validation failed", message, httpServletRequest.getRequestURI());

		return ResponseEntity.badRequest().body(errorResponse);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception exception,
			HttpServletRequest httpServletRequest) {

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server Error", exception.getMessage(), httpServletRequest.getRequestURI());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

}
