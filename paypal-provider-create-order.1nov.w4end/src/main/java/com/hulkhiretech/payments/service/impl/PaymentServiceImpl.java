package com.hulkhiretech.payments.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.http.HttpServiceEngine;
import com.hulkhiretech.payments.paypal.res.PaypalOrder;
import com.hulkhiretech.payments.pojo.CreateOrderReq;
import com.hulkhiretech.payments.pojo.OrderResponse;
import com.hulkhiretech.payments.service.TokenService;
import com.hulkhiretech.payments.service.helper.CreateOrderHelper;
import com.hulkhiretech.payments.service.interfaces.PaymentService;
import com.hulkhiretech.payments.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	
	private final TokenService tokenService;
	
	private final HttpServiceEngine httpServiceEngine;
	
	private final JsonUtil jsonUtil;
	
	@Value("${paypal.create.order.url}")
	private String createOrderUrl;
	
	private final CreateOrderHelper	createOrderHelper;
	
	@Override
	public OrderResponse createOrder(CreateOrderReq createOrderReq) {
		log.info("Creating order in PaymentServiceImpl|| createOrderReq:{}",
				createOrderReq);
		
		String accessToken = tokenService.getAccessToken();
		log.info("Access token retrieved: {}", accessToken);
		
		HttpRequest httpRequest = createOrderHelper.prepareCreateOrderHttpRequest(
				createOrderReq, accessToken);
		log.info("Prepared HttpRequest for OAuth call: {}", httpRequest);
		
		ResponseEntity<String> successResponse = httpServiceEngine.makeHttpCall(httpRequest);
		log.info("HTTP response from HttpServiceEngine: {}", successResponse);

		PaypalOrder paypalOrder = jsonUtil.fromJson(
				successResponse.getBody(), PaypalOrder.class);
		log.info("Converted response body to PaypalOrder: {}", paypalOrder);
		
		// TODO Failure/TimeOut - Proper response handling

		OrderResponse orderResponse = createOrderHelper.toOrderResponse(paypalOrder);
		log.info("Converted OrderResponse: {}", orderResponse);
		
		return orderResponse;
	}
}
