package com.own.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class PaypalProviderException extends RuntimeException {

	private String errorCode;
	private String errorMessage;
	private HttpStatus httpStatus;

	public PaypalProviderException(String errorCode, String errorMessage, HttpStatus httpStatus) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;

	}

}
