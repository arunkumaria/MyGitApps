package com.own.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

	@Value("${paypal.client.id}")
	private String clientId;

	@Value("${paypal.client.secret}")
	private String clientSecret;

	@Value("${paypal.oauth.url}")
	private String oauthUrl;

	private static String accessToken;

	private final HttpServiceEngine httpServiceEngine;

	private final JsonUtil jsonUtil;

	public String getAccessToken() throws Exception {
		if (accessToken != null) {
			log.info("Returning cached access token");
			return accessToken;
		}

		HttpHeaders headers = new HttpHeaders();

		headers.setBasicAuth(clientId, clientSecret);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> formData = new org.springframework.util.LinkedMultiValueMap<>();
		formData.add(Constants.GRANT_TYPE, Constants.CLIENT_CREDENTIALS);

		HttpRequest httpRequest = new HttpRequest();

		httpRequest.setHttpMethod(org.springframework.http.HttpMethod.POST);
		httpRequest.setUrl(oauthUrl);
		httpRequest.setHttpHeaders(headers);
		httpRequest.setBody(formData);

		ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);

		String tokenBody = response.getBody();
		PaypalOAuthToken paypalOAuthToken = jsonUtil.fromJson(tokenBody, PaypalOAuthToken.class);

		log.info("token retrieved is: {}", paypalOAuthToken.getAccessToken());
		return paypalOAuthToken.getAccessToken();

	}

}
