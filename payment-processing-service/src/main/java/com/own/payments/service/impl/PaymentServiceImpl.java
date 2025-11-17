package com.own.payments.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.own.payments.dao.interfaces.TransactionDao;
import com.own.payments.dto.TransactionDto;
import com.own.payments.entity.TransactionEntity;
import com.own.payments.http.HttpRequest;
import com.own.payments.http.HttpServiceEngine;
import com.own.payments.paypalprovider.PPOrderResponse;
import com.own.payments.pojo.CreatePaymentRequest;
import com.own.payments.pojo.InitiatePaymentRequest;
import com.own.payments.pojo.PaymentResponse;
import com.own.payments.service.PaymentStatusService;
import com.own.payments.service.helper.PPCreateOrderHelper;
import com.own.payments.service.interfaces.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PPCreateOrderHelper ppCreateOrderHelper;

	private final HttpServiceEngine httpServiceEngine;

	private final PaymentStatusService paymentStatusService;

	private final ModelMapper modelMapper;
	
	private final TransactionDao transactionDao;

	@Override
	public PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest) {
		log.info("Creating payment in PaymentServiceImpl..."
				+ "||createPaymentRequest:{}",
				createPaymentRequest);

		
		TransactionDto txnDto = modelMapper.map(
				createPaymentRequest, TransactionDto.class);
		log.info("Mapped CreatePaymentRequest to TransactionDto: {}", txnDto);


		int txnStatusId = 1; // CREATED
		String txnReference = generateUniqueTxnReference(); // for every payment, have unique reference
		
		txnDto.setTxnStatusId(txnStatusId);
		txnDto.setTxnReference(txnReference);
		
		TransactionDto response = paymentStatusService.processPayment(txnDto);
		log.info("Response from TransactionStatusProcessor: {}", response);
		
		PaymentResponse paymentRes = new PaymentResponse();
		paymentRes.setTxnReference(response.getTxnReference());
		paymentRes.setTxnStatusId(response.getTxnStatusId());
		log.info("Prepared PaymentResponse: {}", paymentRes);
		
		return paymentRes;
	}

	private String generateUniqueTxnReference() {
		return UUID.randomUUID().toString();
	}

	@Override
	public PaymentResponse initiatePayment(String txnReference, 
			InitiatePaymentRequest initiatePaymentRequest) {
		log.info("Initiating payment in PaymentServiceImpl... "
				+ "txnReference: {} | initiatePaymentRequest:{}", 
				txnReference, initiatePaymentRequest);
		
		TransactionEntity txnEntity = transactionDao.getTransactionByTxnReference(txnReference);
		log.info("Fetched TransactionEntity from DB: {}", txnEntity);
		
		// use modelMapper to convert Entity to DTO
		TransactionDto txnDto = modelMapper.map(
				txnEntity, TransactionDto.class);
		log.info("Mapped TransactionEntity to TransactionDto: {}", txnDto);
		
		// update txn status to INITIATED
		txnDto.setTxnStatusId(2); // INITIATED
		TransactionDto response = paymentStatusService.processPayment(txnDto);
		log.info("Response from PaymentStatusService after updating status to INITIATED: {}", response);
		
		
		// MAKE API CALL To payal-provider to createOrder API
		/*
		 * 1. Prepare HttpRequest - DONE
		 * 2. Pass to HttpServiceEngine
		 * 3. Process the response
		 */

		HttpRequest httpReq = ppCreateOrderHelper.prepareHttpRequest(
				txnReference, initiatePaymentRequest, txnDto);
		log.info("Prepared HttpRequest for PayPalProvider create order: {}", httpReq);

		ResponseEntity<String> httpResponse = httpServiceEngine.makeHttpCall(httpReq);
		log.info("HTTP response from HttpServiceEngine: {}", httpResponse);
		
		PPOrderResponse ppOrderResponse = ppCreateOrderHelper.processResponse(httpResponse);
		log.info("Processed PayPal order response: {}", ppOrderResponse);
		
		// update txn status to PENDING
		txnDto.setTxnStatusId(3); // PENDING
		txnDto.setProviderReference(ppOrderResponse.getOrderId());
		response = paymentStatusService.processPayment(txnDto);
		
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setTxnReference(txnDto.getTxnReference());
		paymentResponse.setTxnStatusId(txnDto.getTxnStatusId());
		paymentResponse.setProviderReference(ppOrderResponse.getOrderId());
		paymentResponse.setRedirectUrl(ppOrderResponse.getRedirectUrl());

		log.info("Final PaymentResponse to be returned: {}", paymentResponse);
		
		return paymentResponse;
	}

	@Override
	public String capturePayment(String txnReference) {
		log.info("Capturing payment in PaymentServiceImpl... "
				+ "txnReference: {}", txnReference);
		return "Payment captured successfully from service! - " + txnReference;
	}

}
