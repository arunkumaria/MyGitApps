package com.own.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.own.http.HttpRequest;
import com.own.http.HttpServiceEngine;
import com.own.payment.dto.TransactionDto;
import com.own.pojo.CreateOrderRequest;
import com.own.pojo.CreatePaymentRequest;
import com.own.pojo.OrderResponse;
import com.own.pojo.PaymentResponse;
import com.own.service.TokenService;
import com.own.service.ValidatorService;
import com.own.service.helper.CaptureOrderHelper;
import com.own.service.helper.CreateOrderHelper;
import com.own.service.interfaces.PaypalProviderServiceInterface;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaypalProviderServiceImpl implements PaypalProviderServiceInterface {

	// 1. validation
	private final ValidatorService validatorService;

	// 2. get the access token
	private final TokenService tokenService;

	// 3. preparing the request and handling the response
	private final CreateOrderHelper createOrderHelper;
	private final CaptureOrderHelper captureOrderHelper;

	// 4. making the http call
	private final HttpServiceEngine httpServiceEngine;

	private final ModelMapper modelMapper;
	private final PaymentStatusService paymentStatusService;

	@Override
	public OrderResponse createOrderProcessor(CreateOrderRequest createOrderRequest)
			throws JsonMappingException, JsonProcessingException {

		log.info("validation started");
		validatorService.validation(createOrderRequest);
		log.info("validation ended");

		log.info("token generation started");
		String accessToken = tokenService.getAccessToken(createOrderRequest);
		log.info("token generation ended with token: {}", accessToken);

		log.info("prepare request started");
		HttpRequest httpRequest = createOrderHelper.prepareHttpRequest(createOrderRequest, accessToken);
		log.info("prepare request ended");

		log.info("make Httpcall started");
		ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);
		log.info("make Httpcall ended");

		log.info("handle response started");
		OrderResponse orderResponse = createOrderHelper.handleHttpResponse(response);
		log.info("handle response started");

		return orderResponse;
	}

	@Override
	public OrderResponse captureOrderProcessor(String orderId) throws JsonMappingException, JsonProcessingException {

		log.info("token generation started");
		String accessToken = tokenService.getAccessToken(null);
		log.info("token generation ended with token: {}", accessToken);

		log.info("prepare request started");
		HttpRequest httpRequest = captureOrderHelper.prepareCaptureOrderRequest(orderId, accessToken);
		log.info("prepare request ended");

		log.info("make Httpcall started");
		ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);
		log.info("make Httpcall ended");

		log.info("handle response started");
		OrderResponse orderResponse = captureOrderHelper.handleResponse(response);
		log.info("handle response started");

		return orderResponse;
	}

	@Override
	public PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest) throws Exception {

		TransactionDto transactionDto = modelMapper.map(createPaymentRequest, TransactionDto.class);

		transactionDto.setTxnStatusId(1);
		transactionDto.setTxnReference(UUID.randomUUID().toString());

		TransactionDto response = paymentStatusService.processPayment(transactionDto);

		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setTxnReference(response.getTxnReference());
		paymentResponse.setTxnStatusId(response.getTxnStatusId());

		return paymentResponse;
	}

}
