package com.own.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.own.pojo.CreateOrderRequest;
import com.own.pojo.OrderResponse;
import com.own.service.interfaces.PaypalProviderServiceInterface;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class PaypalController {

	private final PaypalProviderServiceInterface paypalProviderServiceInterface;

	@PostMapping
	public OrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest)
			throws JsonMappingException, JsonProcessingException {

		log.info("PaypalController-createOrder entered with request: {}", createOrderRequest);
		OrderResponse orderResponse = paypalProviderServiceInterface.createOrderProcessor(createOrderRequest);
		log.info("PaypalController-createOrder exited with response: {}", orderResponse);
		return orderResponse;
	}

	@PostMapping("/{orderId}/capture")
	public OrderResponse captureOrder(@PathVariable String orderId)
			throws JsonMappingException, JsonProcessingException {

		log.info("PaypalController-captureOrder entered with orderId: {}", orderId);
		OrderResponse orderResponse = paypalProviderServiceInterface.captureOrderProcessor(orderId);
		log.info("PaypalController-captureOrder exited with response: {}", orderResponse);
		return orderResponse;

	}

}
