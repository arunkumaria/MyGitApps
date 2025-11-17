package com.own.payments.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.payments.pojo.CreatePaymentRequest;
import com.own.payments.pojo.InitiatePaymentRequest;
import com.own.payments.pojo.PaymentResponse;
import com.own.payments.service.interfaces.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {
	
	private final PaymentService paymentService;
	
	@PostMapping
	public PaymentResponse createPayment(@RequestBody CreatePaymentRequest createPaymentRequest) {
		log.info("Creating payment... ||createPaymentRequest:{}",
				createPaymentRequest);
		
		PaymentResponse response = paymentService.createPayment(createPaymentRequest);
		log.info("Payment creation response from service: {}", response);
		
		return response;
	}
	
	@PostMapping("/{txnReference}/initiate")
	public PaymentResponse initiatePayment(@PathVariable String txnReference,
			@RequestBody InitiatePaymentRequest initiatePaymentRequest) {
		log.info("Initiating payment... txnReference: {}| initiatePaymentRequest:{}", 
				txnReference, initiatePaymentRequest);
		
		PaymentResponse response = paymentService.initiatePayment(
				txnReference, initiatePaymentRequest);
		log.info("Payment initiation response from service: {}", response);
		
		return response;
	}
	
	@PostMapping("/{txnReference}/capture")
	public String capturePayment(@PathVariable String txnReference) {
		log.info("Capturing payment... txnReference: {}", txnReference);
		
		String response = paymentService.capturePayment(txnReference);
		log.info("Payment capture response from service: {}", response);
		
		return response;
	}

}
