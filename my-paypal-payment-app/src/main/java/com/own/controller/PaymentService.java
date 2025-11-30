package com.own.controller;

public interface PaymentService {
	public OrderResponse createOrder(CreateOrderReq createOrderReq);

	public OrderResponse captureOrder(String orderId);

	public PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest) throws Exception;

	public PaymentResponse initiatePayment(String txnReference, InitiatePaymentRequest initiatePaymentRequest)
			throws Exception;

	public PaymentResponse capturePayment(String txnReference) throws Exception;
}
