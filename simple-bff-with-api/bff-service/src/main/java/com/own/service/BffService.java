package com.own.service;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.own.dto.OrderResponse;
import com.own.dto.RestaurantResponse;

@Service
public class BffService {

	private final RestTemplate restTemplate;

	public BffService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private final String ORDER_SERVICE = "http://localhost:8081/api/orders";

	private final String RESTAURANT_SERVICE = "http://localhost:8083/api/restaurants";

	private final String FOOD_SERVICE = "http://localhost:8084/api/foods";

	public OrderResponse getOrder(Long id) {

		ResponseEntity<OrderResponse> response = restTemplate.exchange(ORDER_SERVICE + "/" + id, HttpMethod.GET, null,
				OrderResponse.class);

		return response.getBody();
	}

	public RestaurantResponse getRestaurant(Long id) {

		ResponseEntity<RestaurantResponse> response = restTemplate.exchange(RESTAURANT_SERVICE + "/" + id,
				HttpMethod.GET, null, RestaurantResponse.class);

		return response.getBody();
	}

	public List getFoods() {

		ResponseEntity<List> response = restTemplate.exchange(FOOD_SERVICE, HttpMethod.GET, null, List.class);

		return response.getBody();
	}
}
