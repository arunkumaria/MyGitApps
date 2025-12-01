package com.own.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.own.constant.Constant;
import com.own.http.HttpRequest;
import com.own.http.HttpServiceEngine;
import com.own.paypal.response.PaypalOAuthToken;
import com.own.pojo.CreateOrderRequest;
import com.own.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

	@Value("${paypal.client.id}")
	private String clientId;

	@Value("${paypal.client.secret}")
	private String clientSecret;

	@Value("${paypal.oauth.url}")
	private String oauthUrl;

	private static final String PAYPAL_TOKEN_REDIS = "PAYPAL_TOKEN_REDIS";
	private static final int DIFF = 60;

	private final RedisService redisService;
	
	String accessToken;

	//private static String accessToken;

	private final HttpServiceEngine httpServiceEngine;

	private final JsonUtil jsonUtil;

	public String getAccessToken(CreateOrderRequest createOrderRequest)
			throws JsonMappingException, JsonProcessingException {

		//String accessToken = redisService.getValue(PAYPAL_TOKEN_REDIS);// get from redis

		log.info("TokenService-getAccessToken entered with request: {}", createOrderRequest);
		if (accessToken != null) {
			return accessToken; // return from redis
		}

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setBasicAuth(clientId, clientSecret);
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.add(Constant.GRANT_TYPE, Constant.CLIENT_CREDENTIALS);

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setHttpMethod(HttpMethod.POST);
		httpRequest.setUrl(oauthUrl);
		httpRequest.setHttpHeaders(httpHeaders);
		httpRequest.setBody(multiValueMap);

		ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);

		PaypalOAuthToken paypalOAuthToken = jsonUtil.fromJson(response.getBody(), PaypalOAuthToken.class);

		accessToken = paypalOAuthToken.getAccessToken();

		//redisService.setValueWithExpiry(PAYPAL_TOKEN_REDIS, accessToken,paypalOAuthToken.getExpiresIn() - DIFF);

		log.info("TokenService-getAccessToken entered with accessToken: {}", accessToken);

		return accessToken;

	}

}
