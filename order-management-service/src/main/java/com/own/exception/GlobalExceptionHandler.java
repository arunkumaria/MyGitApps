package com.own.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

		ApiError error = ApiError.builder().timestamp(LocalDateTime.now()).status(HttpStatus.NOT_FOUND.value())
				.error("NOT_FOUND").message(ex.getMessage()).path(request.getRequestURI()).build();

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest request) {

		ApiError error = ApiError.builder().timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value())
				.error("BAD_REQUEST").message(ex.getMessage()).path(request.getRequestURI()).build();

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {

		ApiError error = ApiError.builder().timestamp(LocalDateTime.now()).status(HttpStatus.UNAUTHORIZED.value())
				.error("UNAUTHORIZED").message(ex.getMessage()).path(request.getRequestURI()).build();

		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {

		ApiError error = ApiError.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error("INTERNAL_SERVER_ERROR")
				.message("Unexpected error occurred").path(request.getRequestURI()).build();

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
