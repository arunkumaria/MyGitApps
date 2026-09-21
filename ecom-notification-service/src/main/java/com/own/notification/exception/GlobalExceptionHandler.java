package com.own.notification.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotificationNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotificationNotFound(NotificationNotFoundException exception) {

		Map<String, Object> response = new HashMap<>();

		response.put("timestamp", LocalDateTime.now());

		response.put("status", HttpStatus.NOT_FOUND.value());

		response.put("error", "Notification Not Found");

		response.put("message", exception.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception) {

		Map<String, Object> response = new HashMap<>();

		response.put("timestamp", LocalDateTime.now());

		response.put("status", HttpStatus.BAD_REQUEST.value());

		response.put("error", "Validation Failed");

		Map<String, String> errors = new HashMap<>();

		exception.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		response.put("errors", errors);

		return ResponseEntity.badRequest().body(response);
	}
}
