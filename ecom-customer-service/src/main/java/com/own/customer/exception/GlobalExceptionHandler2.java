package com.own.customer.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler2 {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse2> handleGenericException(Exception ex, HttpServletRequest httpServletRequest) {
		ErrorResponse2 errorResponse = new ErrorResponse2("Internal Server Error", ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), httpServletRequest.getRequestURI(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse2> handleCustomerNotFoundException(CustomerNotFoundException2 ex,
			HttpServletRequest httpServletRequest) {
		ErrorResponse2 errorResponse2 = new ErrorResponse2("Customer Not Found", ex.getMessage(),
				HttpStatus.NOT_FOUND.value(), httpServletRequest.getRequestURI(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse2);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse2> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest httpServletRequest) {
		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ":" + error.getDefaultMessage()).collect(Collectors.joining(","));
		ErrorResponse2 errorResponse2 = new ErrorResponse2("Not Valid", message, HttpStatus.BAD_REQUEST.value(),
				httpServletRequest.getRequestURI(), LocalDateTime.now());
		return ResponseEntity.badRequest().body(errorResponse2);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse2> handleIllegalArgumentException(IllegalArgumentException ex,
			HttpServletRequest httpServletRequest) {
		ErrorResponse2 errorResponse2 = new ErrorResponse2("Bad Request", ex.getMessage(),
				HttpStatus.BAD_REQUEST.value(), httpServletRequest.getRequestURI(), LocalDateTime.now());
		return ResponseEntity.badRequest().body(errorResponse2);
	}

}
