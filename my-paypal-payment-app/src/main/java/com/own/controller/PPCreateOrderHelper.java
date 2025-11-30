package com.own.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PPCreateOrderHelper {

	@Value("${paypalprovider.createorder.url}")
	private String createOrderUrl;

	private final JsonUtil jsonUtil;

	public HttpRequest prepareRequest(String txnReference, InitiatePaymentRequest request,
			TransactionDto transactionDto) {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		PPCreateOrderRequest createOrderRequest = new PPCreateOrderRequest();
		createOrderRequest.setAmount(transactionDto.getAmount().doubleValue());
		createOrderRequest.setCurrencyCode(transactionDto.getCurrency());
		createOrderRequest.setReturnUrl(request.getSuccessUrl());
		createOrderRequest.setCancelUrl(request.getCancelUrl());

		String requestAsBody = jsonUtil.toJson(createOrderRequest);

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setHttpMethod(HttpMethod.POST);
		httpRequest.setUrl(createOrderUrl);
		httpRequest.setHttpHeaders(httpHeaders);
		httpRequest.setBody(requestAsBody);

		return httpRequest;

	}

	public PPOrderResponse processResponse(ResponseEntity<String> response) throws Exception {
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			PPOrderResponse ppOrderResponse = jsonUtil.fromJson(response.getBody(), PPOrderResponse.class);
			if (ppOrderResponse != null && ppOrderResponse.getOrderId() != null
					&& ppOrderResponse.getPaypalStatus() != null && ppOrderResponse.getRedirectUrl() != null) {

				return ppOrderResponse;
			} else {
				log.info("process response failed");
			}
		}

		if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
			throw new Exception();
		}

		throw new Exception();
	}

}
