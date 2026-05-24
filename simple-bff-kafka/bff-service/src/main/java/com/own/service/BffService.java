package com.own.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.own.model.OrderRequest;

@Service
public class BffService {

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * DASHBOARD API
	 */
	public Map<String, Object> dashboard() {

		System.out.println("CALLING FOOD SERVICE");

		ResponseEntity<List<Map<String, Object>>> foodsResponse = restTemplate.exchange("http://FOOD-SERVICE/foods",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Map<String, Object>>>() {
				});

		System.out.println("FOOD RESPONSE = " + foodsResponse.getBody());

		System.out.println("CALLING RESTAURANT SERVICE");

		ResponseEntity<List<Map<String, Object>>> restaurantResponse = restTemplate.exchange(
				"http://RESTAURANT-SERVICE/restaurants", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Map<String, Object>>>() {
				});

		System.out.println("RESTAURANT RESPONSE = " + restaurantResponse.getBody());

		Map<String, Object> response = new HashMap<>();

		response.put("foods", foodsResponse.getBody());

		response.put("restaurants", restaurantResponse.getBody());

		return response;
	}

	/*
	 * PLACE ORDER API
	 */
	public String placeOrder(OrderRequest request) {

		try {

			System.out.println("INSIDE BFF SERVICE");

			HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<OrderRequest> entity = new HttpEntity<>(request, headers);

			System.out.println("CALLING ORDER SERVICE");

			ResponseEntity<String> response = restTemplate.exchange("http://ORDER-SERVICE/orders", HttpMethod.POST,
					entity, String.class);

			System.out.println("ORDER RESPONSE = " + response.getBody());

			return response.getBody();

		} catch (Exception e) {

			e.printStackTrace();

			return "ERROR = " + e.getMessage();
		}
	}
}