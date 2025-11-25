package com.own.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import com.own.constant.ErrorCodeEnum;
import com.own.exceptions.PaypalProviderException;
import com.own.pojo.CreateOrderRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidatorService {

	public void validation(CreateOrderRequest createOrderRequest) {

		log.info("entered the validation method");
		if (createOrderRequest == null) {
			throw new PaypalProviderException(ErrorCodeEnum.GENERIC_ERROR.getErrorCode(),
					ErrorCodeEnum.GENERIC_ERROR.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

		if (createOrderRequest.getCurrencyCode() == null || createOrderRequest.getCurrencyCode().isBlank()) {
			throw new PaypalProviderException(ErrorCodeEnum.CURRENCY_CODE_REQUIRED.getErrorCode(),
					ErrorCodeEnum.CURRENCY_CODE_REQUIRED.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

		if (createOrderRequest.getAmount() == null || createOrderRequest.getAmount() <= 0) {
			throw new PaypalProviderException(ErrorCodeEnum.INVALID_AMOUNT.getErrorCode(),
					ErrorCodeEnum.INVALID_AMOUNT.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

		if (createOrderRequest.getReturnUrl() == null || createOrderRequest.getReturnUrl().isBlank()) {
			throw new PaypalProviderException(ErrorCodeEnum.RETURN_URL_REQUIRED.getErrorCode(),
					ErrorCodeEnum.RETURN_URL_REQUIRED.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

		if (createOrderRequest.getCancelUrl() == null || createOrderRequest.getCancelUrl().isBlank()) {
			throw new PaypalProviderException(ErrorCodeEnum.CANCEL_URL_REQUIRED.getErrorCode(),
					ErrorCodeEnum.CANCEL_URL_REQUIRED.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

		log.info("exited the validation method");

	}

}
