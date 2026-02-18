package com.own.controller;

import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/hello")
	public String check() {
		return "hello";
	}

	@PostMapping("/create-order")
	public OrderResponse createOrder(@RequestBody CreateOrderReq createOrderReq) throws Exception {

		log.info("create order request: {}", createOrderReq);
		OrderResponse orderRes = paymentService.createOrder(createOrderReq);
		log.info("create order response: {}", orderRes);

		return orderRes;

	}
	
	@PostMapping("/capture-order/{orderId}/capture")
	public OrderResponse captureOrder(@PathVariable String orderId) throws Exception {

		log.info("captureOrder request with order id: {}", orderId);
		OrderResponse orderRes = paymentService.captureOrder(orderId);
		log.info("captureOrder response: {}", orderRes);

		return orderRes;

	}
	
	@PostMapping("/create-payment")
	public PaymentResponse createPayment(@RequestBody CreatePaymentRequest createPaymentRequest) throws Exception {

		log.info("create payment request: {}", createPaymentRequest);
		PaymentResponse paymentResponse = paymentService.createPayment(createPaymentRequest);
		log.info("create payment response: {}", paymentResponse);

		return paymentResponse;

	}
	
	
	@PostMapping("/initiate-payment/{txnReference}/initiate")
	public PaymentResponse createPayment(@PathVariable String txnReference, @RequestBody InitiatePaymentRequest initiatePaymentRequest) throws Exception {

		log.info("initiate-payment txnReference: {}", txnReference);
		PaymentResponse paymentResponse = paymentService.initiatePayment(txnReference, initiatePaymentRequest);
		log.info("initiate-payment response: {}", paymentResponse);

		return paymentResponse;

	}
	
	@PostMapping("/capture-payment/{txnReference}/capture")
	public PaymentResponse capturePayment(@PathVariable String txnReference) throws Exception {

		log.info("capture-payment txnReference: {}", txnReference);
		PaymentResponse paymentResponse = paymentService.capturePayment(txnReference);
		log.info("capture-payment response: {}", paymentResponse);

		return paymentResponse;

	}

}
