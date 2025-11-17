package com.own.payments.service.interfaces;

import com.own.payments.pojo.CreatePaymentRequest;
import com.own.payments.pojo.InitiatePaymentRequest;
import com.own.payments.pojo.PaymentResponse;

public interface PaymentService {
	
	public PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest);
	
	public PaymentResponse initiatePayment(String txnReference, 
			InitiatePaymentRequest initiatePaymentRequest);
	
	public String capturePayment(String txnReference);

}
