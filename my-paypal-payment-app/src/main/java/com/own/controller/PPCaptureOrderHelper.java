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
public class PPCaptureOrderHelper {

	@Value("${paypalprovider.captureorder.url}")
	private String captureOrderUrl;

	private final JsonUtil jsonUtil;

	public HttpRequest prepareRequest(String txnReference, TransactionDto transactionDto) {

		HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String newCaptureOrderUrl = captureOrderUrl.replace("{provider-reference}",
				transactionDto.getProviderReference());

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setHttpMethod(HttpMethod.POST);
		httpRequest.setUrl(newCaptureOrderUrl);
		httpRequest.setHttpHeaders(headers);
		httpRequest.setBody("");

		return httpRequest;

	}

	public PPOrderResponse processResponse(ResponseEntity<String> response) throws Exception {

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			PPOrderResponse ppOrderResponse = jsonUtil.fromJson(response.getBody(), PPOrderResponse.class);
			if (ppOrderResponse != null && ppOrderResponse.getOrderId() != null
					&& ppOrderResponse.getPaypalStatus() != null
					&& ppOrderResponse.getPaypalStatus().equals("COMPLETED")) {
				return ppOrderResponse;
			} else {
				log.error("ppOrderResponse error");
			}

		}

		if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {

			ErrorResponse errorResponse = jsonUtil.fromJson(response.getBody(), ErrorResponse.class);

			throw new PaymentException(errorResponse.getErrorCode(), errorResponse.getErrorMessage(),
					HttpStatus.valueOf(response.getStatusCode().value()));
			// throw new Exception();
		}

		throw new Exception();

	}

}
