package com.own.service.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.own.http.HttpRequest;
import com.own.payment.dto.TransactionDto;
import com.own.pojo.InitiatePaymentRequest;
import com.own.pojo.PPCreateOrderRequest;
import com.own.pojo.PPOrderResponse;
import com.own.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PPCreateOrderHelper {

	@Value("${paypalprovider.createorder.url}")
	private String createOrderUrl;

	private final JsonUtil jsonUtil;

	public HttpRequest prepareHttpRequest(String txnReference, InitiatePaymentRequest initiatePaymentRequest,
			TransactionDto transactionDto) throws JsonProcessingException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		PPCreateOrderRequest ppCreateOrderRequest = new PPCreateOrderRequest();
		ppCreateOrderRequest.setAmount(transactionDto.getAmount().doubleValue());
		ppCreateOrderRequest.setCurrencyCode(transactionDto.getCurrency());
		ppCreateOrderRequest.setReturnUrl(initiatePaymentRequest.getSuccessUrl());
		ppCreateOrderRequest.setCancelUrl(initiatePaymentRequest.getCancelUrl());

		String requestAsJson = jsonUtil.toJson(ppCreateOrderRequest);

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setHttpMethod(HttpMethod.POST);
		httpRequest.setUrl(createOrderUrl);
		httpRequest.setHttpHeaders(httpHeaders);
		httpRequest.setBody(requestAsJson);

		return httpRequest;

	}

	public PPOrderResponse processPayment(ResponseEntity<String> response)
			throws Exception {
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			PPOrderResponse ppOrderResponse = jsonUtil.fromJson(response.getBody(), PPOrderResponse.class);

			if (ppOrderResponse != null && ppOrderResponse.getOrderId() != null
					&& ppOrderResponse.getPaypalStatus() != null && ppOrderResponse.getRedirectUrl() != null) {

				return ppOrderResponse;

			} else {
				log.error("ppOrderResponse: {}", ppOrderResponse);
			}
		}

		if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
			throw new Exception();
		}

		throw new Exception();
	}

}
