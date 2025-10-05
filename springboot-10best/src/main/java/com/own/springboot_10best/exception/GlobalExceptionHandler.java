package com.own.springboot_10best.exception;

import com.example.toolkit.common.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ApiError> notFound(NoSuchElementException ex, WebRequest req) {
		ApiError api = new ApiError(Instant.now(), HttpStatus.NOT_FOUND.value(), "Resource not found",
				req.getDescription(false));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(api);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> generic(Exception ex, WebRequest req) {
		ApiError api = new ApiError(Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
				req.getDescription(false));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
	}
}