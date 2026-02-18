package com.own.controller;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	// validation
	private final PaymentValidator paymentValidator;

	// get the access token
	private final TokenService tokenService;

	// prepare the request and handle the response
	private final CreateOrderHelper createOrderHelper;
	private final CaptureOrderHelper capturOrderHelper;

	// make the request
	private final HttpServiceEngine httpServiceEngine;

	private final ModelMapper modelMapper;
	private final PaymentStatusService paymentStatusService;
	private final TransactionDao transactionDao;
	private final PPCreateOrderHelper ppCreateOrder;
	private final PPCaptureOrderHelper ppCaptureOrderHelper;

	@Override
	public OrderResponse createOrder(CreateOrderReq createOrderReq) throws Exception {
		log.info("payment service entered");

		paymentValidator.validation(createOrderReq);

		log.info("validation successful");

		String accessToken = tokenService.getAccessToken();

		log.info("token retrieval is successful");

		HttpRequest httpRequest = createOrderHelper.prepareHttpRequest(createOrderReq, accessToken);
		log.info("request preparation is successful with request: {}", httpRequest);

		ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);
		log.info("request making is successful");

		OrderResponse orderResponse = createOrderHelper.handlePaypalResponse(response);
		log.info("response is successful");

		return orderResponse;

	}

	@Override
	public OrderResponse captureOrder(String orderId) throws Exception {
		log.info("capture order enteredwith orderId: {}", orderId);

		String accessToken = tokenService.getAccessToken();

		HttpRequest httpRequest = capturOrderHelper.prepareCaptureOrderHttpRequest(orderId, accessToken);

		ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);

		OrderResponse orderResponse = capturOrderHelper.handleCaptureOrderResponse(response);

		log.info("capture order successful with response: {}", orderResponse);
		return orderResponse;

	}

	@Override
	public PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest) throws Exception {

		TransactionDto transactionDto = modelMapper.map(createPaymentRequest, TransactionDto.class);
		int txnStatusId = 1;
		String txnReference = getTxnReference();

		transactionDto.setTxnStatusId(txnStatusId);
		transactionDto.setTxnReference(txnReference);

		TransactionDto response = paymentStatusService.processPayment(transactionDto);

		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setTxnReference(response.getTxnReference());
		paymentResponse.setTxnStatusId(response.getTxnStatusId());

		return paymentResponse;

	}

	public String getTxnReference() {
		return UUID.randomUUID().toString();
	}

	@Override
	public PaymentResponse initiatePayment(String txnReference, InitiatePaymentRequest initiatePaymentRequest)
			throws Exception {
		log.info("PaymentServiceImpl-initiatePayment entry");

		// transaction dto formation
		TransactionEntity transactionEntity = transactionDao.getTransaction(txnReference);
		TransactionDto transactionDto = modelMapper.map(transactionEntity, TransactionDto.class);
		log.info("transaction dto formation completed");

		// saving the initiated status to db
		transactionDto.setTxnStatusId(2);
		transactionDto = paymentStatusService.processPayment(transactionDto);
		log.info("saving the initiated status to db completed");

		// order id generation
		HttpRequest httpRequest = ppCreateOrder.prepareRequest(txnReference, initiatePaymentRequest, transactionDto);
		ResponseEntity<String> httpResponse = httpServiceEngine.makeHttpCall(httpRequest);
		PPOrderResponse ppOrderResponse = ppCreateOrder.processResponse(httpResponse);
		log.info("order id generation completed");

		// saving the updated status to db with order id
		transactionDto.setTxnStatusId(3);
		transactionDto.setProviderReference(ppOrderResponse.getOrderId());
		transactionDto = paymentStatusService.processPayment(transactionDto);
		log.info(" saving the updated status to db with order id completed");

		// returning the payment response
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setTxnStatusId(transactionDto.getTxnStatusId());
		paymentResponse.setTxnReference(transactionDto.getTxnReference());
		paymentResponse.setProviderReference(ppOrderResponse.getOrderId());
		paymentResponse.setRedirectUrl(ppOrderResponse.getRedirectUrl());
		log.info("returning the payment response completed");

		log.info("PaymentServiceImpl-initiatePayment exit with response: {}", paymentResponse);
		return paymentResponse;
	}

	@Override
	public PaymentResponse capturePayment(String txnReference) throws Exception {
		// dto generation
		TransactionEntity transactionEntity = transactionDao.getTransaction(txnReference);
		TransactionDto transactionDto = modelMapper.map(transactionEntity, TransactionDto.class);

		// setting the approved status in the db
		transactionDto.setTxnStatusId(4);
		transactionDto = paymentStatusService.processPayment(transactionDto);

		// capture the provider reference
		HttpRequest httpRequest = ppCaptureOrderHelper.prepareRequest(txnReference, transactionDto);

		ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);
		PPOrderResponse ppOrderResponse = ppCaptureOrderHelper.processResponse(response);

		// setting the success status
		transactionDto.setTxnStatusId(5);
		transactionDto = paymentStatusService.processPayment(transactionDto);

		// returning the Payment response
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setTxnStatusId(transactionDto.getTxnStatusId());
		paymentResponse.setTxnReference(transactionDto.getTxnReference());

		log.info("captured");
		return paymentResponse;
	}

}
