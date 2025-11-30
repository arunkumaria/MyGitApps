package com.own.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HttpServiceEngine {

	private final RestClient restClient;

	public ResponseEntity<String> makeHttpCall(HttpRequest httpRequest) {

		try {
			log.info("entered make http call");
			ResponseEntity<String> response = restClient.method(httpRequest.getHttpMethod()).uri(httpRequest.getUrl())
					.headers(h -> h.addAll(httpRequest.getHttpHeaders())).body(httpRequest.getBody()).retrieve()
					.toEntity(String.class);

			log.info("HTTP call completed httpResponse:{}", response);

			return response;

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			// valid error response from server
//			log.error("HTTP error response received: {}", e.getMessage(), e);
//
//			// if the error is gateway time or service unavailable, throw
//			// PaypalProviderException
//			if (e.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT
//					|| e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
//				log.error("Service is unavailable or gateway timed out");
//				throw new PaymentException(ErrorEnum.SERVICE_UNAVAILABLE.getErrorCode(),
//						ErrorEnum.SERVICE_UNAVAILABLE.getErrorMessage(), HttpStatus.SERVICE_UNAVAILABLE);
//			}
//
//			// return ResponseEntity with error details
			String errorResponse = e.getResponseBodyAsString();
			log.info("Error response body: {}", errorResponse);

			return ResponseEntity.status(e.getStatusCode()).body(errorResponse);

		} catch (Exception e) { // No Response case.
			// log.error("Exception while preparing form data: {}", e.getMessage(), e);

			throw new PaymentException(ErrorEnum.SERVICE_UNAVAILABLE.getErrorCode(),
					ErrorEnum.SERVICE_UNAVAILABLE.getErrorMessage(), HttpStatus.SERVICE_UNAVAILABLE);
		}

	}

}
