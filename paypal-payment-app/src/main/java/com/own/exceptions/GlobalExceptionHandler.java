package com.own.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.own.constant.ErrorCodeEnum;
import com.own.pojo.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PaypalProviderException.class)
	public ResponseEntity<?> handlePaypalExceptions(PaypalProviderException ex) {

		ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());

		return new ResponseEntity<>(errorResponse, ex.getHttpStatus());

	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<?> handleNoResourceFoundExceptionExceptions(NoResourceFoundException ex) {

		ErrorResponse errorResponse = new ErrorResponse(ErrorCodeEnum.RESOURCE_NOT_FOUND.getErrorCode(),
				ErrorCodeEnum.RESOURCE_NOT_FOUND.getErrorMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGenericExceptions(Exception ex) {

		ErrorResponse errorResponse = new ErrorResponse(ErrorCodeEnum.GENERIC_ERROR.getErrorCode(),
				ErrorCodeEnum.GENERIC_ERROR.getErrorMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

}
