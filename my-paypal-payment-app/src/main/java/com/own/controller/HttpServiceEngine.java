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

	public ResponseEntity<String> makeHttpCall(HttpRequest httpRequest) throws Exception {

		log.info("HttpServiceEngine-makeHttpCall entered with request: {}", httpRequest);
		try {
			ResponseEntity<String> response = restClient.method(httpRequest.getHttpMethod()).uri(httpRequest.getUrl())
					.headers(restClientHeaders -> restClientHeaders.addAll(httpRequest.getHttpHeaders()))
					.body(httpRequest.getBody()).retrieve().toEntity(String.class);

			log.info("HttpServiceEngine-makeHttpCall entered with response: {}", response);
			return response;

		} catch (HttpClientErrorException | HttpServerErrorException e) {

			String errorBody = e.getResponseBodyAsString();
			log.error("HttpServiceEngine-makeHttpCall error response: {}", e.getMessage());
			return ResponseEntity.status(e.getStatusCode()).body(errorBody);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

}
