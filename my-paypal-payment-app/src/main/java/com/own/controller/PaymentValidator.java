package com.own.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentValidator {

	public void validation(CreateOrderReq createOrderReq) {

		log.info("validating create order request: {}", createOrderReq);

		if (createOrderReq == null) {
			log.error("invalid request");
			throw new PaymentException(ErrorEnum.INVALID_REQUEST.getErrorCode(),
					ErrorEnum.INVALID_REQUEST.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

		if (createOrderReq.getCurrencyCode() == null || createOrderReq.getCurrencyCode().isBlank()) {
			log.error("invalid currency code");
			throw new PaymentException(ErrorEnum.CURRENCY_CODE_REQUIRED.getErrorCode(),
					ErrorEnum.CURRENCY_CODE_REQUIRED.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

		if (createOrderReq.getAmount() == null || createOrderReq.getAmount() <= 0) {
			
			log.error("invalid amount");
			throw new PaymentException(ErrorEnum.AMOUNT_REQUIRED.getErrorCode(),
					ErrorEnum.AMOUNT_REQUIRED.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

		if (createOrderReq.getReturnUrl() == null || createOrderReq.getReturnUrl().isBlank()) {
			
			log.error("invalid return url");
			throw new PaymentException(ErrorEnum.RETURN_URL_REQUIRED.getErrorCode(),
					ErrorEnum.RETURN_URL_REQUIRED.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

		if (createOrderReq.getCancelUrl() == null || createOrderReq.getCancelUrl().isBlank()) {
			log.error("invalid cancel url");
			throw new PaymentException(ErrorEnum.CANCEL_URL_REQUIRED.getErrorCode(),
					ErrorEnum.CANCEL_URL_REQUIRED.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

		log.info("validation success of the create order request: {}", createOrderReq);

	}

}
