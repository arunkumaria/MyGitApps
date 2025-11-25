package com.own.controller;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.Getter;

@Getter
public enum ErrorEnum {

	GENERIC_ERROR("3000", "unauthorized error occurred"),
	CURRENCY_CODE_REQUIRED("3001", "Currency code wrongly entered"), AMOUNT_REQUIRED("3002", "Amount wrongly entered"),
	RETURN_URL_REQUIRED("3003", "Return url wrongly entered"),
	CANCEL_URL_REQUIRED("3004", "Cancel url wrongly entered"), INVALID_REQUEST("3005", "invalid request entered"),
	SERVICE_UNAVAILABLE("3006", "service unavailable"), UNKNOWN_ERROR("3007", "unknown error occurred"),
	RESOURCE_NOT_FOUND("3008", "resource not found"), PAYPAL_ERROR("3009", "paypal response error");

	private String errorCode;
	private String errorMessage;

	ErrorEnum(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
