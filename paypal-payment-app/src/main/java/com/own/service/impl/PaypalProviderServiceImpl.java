package com.own.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.own.http.HttpRequest;
import com.own.http.HttpServiceEngine;
import com.own.payment.dao.interfaces.TransactionDaoInterface;
import com.own.payment.dto.TransactionDto;
import com.own.payment.entity.TransactionEntity;
import com.own.pojo.CreateOrderRequest;
import com.own.pojo.CreatePaymentRequest;
import com.own.pojo.InitiatePaymentRequest;
import com.own.pojo.OrderResponse;
import com.own.pojo.PPOrderResponse;
import com.own.pojo.PaymentResponse;
import com.own.service.TokenService;
import com.own.service.ValidatorService;
import com.own.service.helper.CaptureOrderHelper;
import com.own.service.helper.CreateOrderHelper;
import com.own.service.helper.PPCaptureOrderHelper;
import com.own.service.helper.PPCreateOrderHelper;
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
	private final TransactionDaoInterface transactionDaoInterface;
	private final PPCreateOrderHelper ppCreateOrderHelper;
	private final PPCaptureOrderHelper ppCaptureOrderHelper;

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

		log.info("start create payment");
		TransactionDto transactionDto = modelMapper.map(createPaymentRequest, TransactionDto.class);

		transactionDto.setTxnStatusId(1); //created
		transactionDto.setTxnReference(UUID.randomUUID().toString());

		log.info("start db response");
		TransactionDto response = paymentStatusService.processPayment(transactionDto);
		log.info("end db response");

		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setTxnReference(response.getTxnReference());
		paymentResponse.setTxnStatusId(response.getTxnStatusId());
		
		log.info("end create payment");

		return paymentResponse;
	}

	@Override
	public PaymentResponse initiatePayment(String txnReference,InitiatePaymentRequest initiatePaymentRequest) throws Exception {
		log.info("PaypalProviderServiceImpl-initiatePayment entry");
		
		//dto generation
		log.info("dto generation started");
		TransactionEntity transactionEntity = transactionDaoInterface.getTransaction(txnReference);
		TransactionDto transactionDto = modelMapper.map(transactionEntity, TransactionDto.class);
		log.info("dto generation ended");
	
		
		//initiate status entry to the database
		log.info("initiate status entry started");
		transactionDto.setTxnStatusId(2); //initiated
		transactionDto = paymentStatusService.processPayment(transactionDto);
		log.info("initiate status entry ended");
		
				
		//order id generation
		log.info("order id generation started");
		HttpRequest httpRequest = ppCreateOrderHelper.prepareHttpRequest(txnReference, initiatePaymentRequest, transactionDto);
		ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);
		PPOrderResponse ppOrderResponse = ppCreateOrderHelper.processPayment(response);
		log.info("order id generation ended");
		
		
		//pending status entry to the database
		log.info("pending status entry to the database started");
		transactionDto.setTxnStatusId(3);
		transactionDto.setProviderReference(ppOrderResponse.getOrderId());
		transactionDto = paymentStatusService.processPayment(transactionDto);
		
		
		log.info("pending status entry to the database ended");
		
		//sending the payment response
		log.info("sending the payment response started");
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setTxnStatusId(transactionDto.getTxnStatusId());
		paymentResponse.setTxnReference(transactionDto.getTxnReference());
		paymentResponse.setProviderReference(ppOrderResponse.getOrderId());
		paymentResponse.setRedirectUrl(ppOrderResponse.getRedirectUrl());
		log.info("sending the payment response ended");
				
		log.info("PaypalProviderServiceImpl-initiatePayment exit");
		return paymentResponse;
	}
	
	@Override
	public PaymentResponse capturePayment(String txnReference) throws Exception {
		log.info("PaypalProviderServiceImpl-capturePayment entry");
		
		//dto generation
		log.info("dto generation started");
		TransactionEntity transactionEntity = transactionDaoInterface.getTransaction(txnReference);
		TransactionDto transactionDto = modelMapper.map(transactionEntity, TransactionDto.class);
		log.info("dto generation ended");
	
		
		//initiate status entry to the database
		log.info("approved status entry started");
		transactionDto.setTxnStatusId(4); //initiated
		transactionDto = paymentStatusService.processPayment(transactionDto);
		log.info("approved status entry ended");
		
				
		//order id generation
		log.info("ppOrderResponse started");
		HttpRequest httpRequest = ppCaptureOrderHelper.prepareRequest(txnReference, transactionDto);
		ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);
		PPOrderResponse ppOrderResponse = ppCaptureOrderHelper.processResponse(response);
		log.info("ppOrderResponse ended");
		
		
		//pending status entry to the database
		log.info("success status entry to the database started");
		transactionDto.setTxnStatusId(5);
		transactionDto = paymentStatusService.processPayment(transactionDto);
		log.info("success status entry to the database ended");
		
		//sending the payment response
		log.info("sending the payment response started");
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setTxnStatusId(transactionDto.getTxnStatusId());
		paymentResponse.setTxnReference(transactionDto.getTxnReference());
		log.info("sending the payment response ended");
				
		log.info("PaypalProviderServiceImpl-capturePayment exit");
		return paymentResponse;
	}

}
