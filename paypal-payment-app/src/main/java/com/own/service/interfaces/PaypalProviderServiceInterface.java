package com.own.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.own.pojo.CreateOrderRequest;
import com.own.pojo.CreatePaymentRequest;
import com.own.pojo.InitiatePaymentRequest;
import com.own.pojo.OrderResponse;
import com.own.pojo.PaymentResponse;

public interface PaypalProviderServiceInterface {

	public OrderResponse createOrderProcessor(CreateOrderRequest createOrderRequest)
			throws JsonMappingException, JsonProcessingException;

	public OrderResponse captureOrderProcessor(String orderId) throws JsonMappingException, JsonProcessingException;

	public PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest) throws Exception;

	public PaymentResponse initiatePayment(String txnReference, InitiatePaymentRequest initiatePaymentRequest)
			throws Exception;

	public PaymentResponse capturePayment(String txnReference) throws Exception;

}
