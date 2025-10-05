package com.own.springboot_crud;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler2 {

	
	@ExceptionHandler(UserNotFoundException2.class)
	public ResponseEntity<String> handleexception(UserNotFoundException2 ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleexception(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}
	
	
}
