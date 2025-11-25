package com.own.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PaymentException.class)
	public ResponseEntity<?> handlePaymentExceptions(PaymentException ex) {

		ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
		return new ResponseEntity<>(errorResponse, ex.getHttpStatus());

	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<?> handlePaymentNoResourceFoundException(NoResourceFoundException ex) {

		ErrorResponse errorResponse = new ErrorResponse(ErrorEnum.RESOURCE_NOT_FOUND.getErrorCode(),
				ErrorEnum.RESOURCE_NOT_FOUND.getErrorMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handlePaymentException(Exception ex) {

		ErrorResponse errorResponse = new ErrorResponse(ErrorEnum.GENERIC_ERROR.getErrorCode(),
				ErrorEnum.GENERIC_ERROR.getErrorMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
