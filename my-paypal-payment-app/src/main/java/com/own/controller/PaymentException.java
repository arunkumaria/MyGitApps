package com.own.controller;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {

	private String errorCode;
	private String errorMessage;
	private HttpStatus httpStatus;

	public PaymentException(String errorCode, String errorMessage, HttpStatus httpStatus) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

}
