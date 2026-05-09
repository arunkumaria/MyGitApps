package com.own.exceptions;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleNotFound(Exception ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
	}

	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<?> handleStock(Exception ex) {
		return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
	}

	@ExceptionHandler(InvalidOrderStateException.class)
	public ResponseEntity<?> handleState(Exception ex) {
		return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
	}
}