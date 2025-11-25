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

	@Override
	public OrderResponse createOrder(CreateOrderReq createOrderReq) {
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
	public OrderResponse captureOrder(String orderId) {
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

}
