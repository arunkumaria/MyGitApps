package com.own.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/create-order")
	public OrderResponse createOrder(@RequestBody CreateOrderReq createOrderReq) {

		log.info("create order request: {}", createOrderReq);
		OrderResponse orderRes = paymentService.createOrder(createOrderReq);
		log.info("create order response: {}", orderRes);

		return orderRes;

	}
	
	@PostMapping("/{orderId}/capture")
	public OrderResponse captureOrder(@PathVariable String orderId) {

		log.info("request with order id: {}", orderId);
		OrderResponse orderRes = paymentService.captureOrder(orderId);
		log.info("capture response: {}", orderRes);

		return orderRes;

	}
	
	@PostMapping("/create-payment")
	public PaymentResponse createPayment(@RequestBody CreatePaymentRequest createPaymentRequest) throws Exception {

		log.info("create payment request: {}", createPaymentRequest);
		PaymentResponse paymentResponse = paymentService.createPayment(createPaymentRequest);
		log.info("create payment response: {}", paymentResponse);

		return paymentResponse;

	}

}
