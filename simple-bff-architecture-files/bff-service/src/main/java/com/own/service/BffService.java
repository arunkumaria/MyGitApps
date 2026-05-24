package com.own.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.own.client.FoodClient;
import com.own.client.RestaurantClient;
import com.own.dto.AggregatedResponse;
import com.own.dto.FoodDTO;
import com.own.dto.RestaurantDTO;

@Service
public class BffService {

	private final RestaurantClient restaurantClient;
	private final FoodClient foodClient;

	public BffService(RestaurantClient restaurantClient, FoodClient foodClient) {

		this.restaurantClient = restaurantClient;
		this.foodClient = foodClient;
	}

	public AggregatedResponse getRestaurantDetails(Long restaurantId) {

		RestaurantDTO restaurant = restaurantClient.getRestaurant(restaurantId);

		List<FoodDTO> foods = foodClient.getFoods(restaurantId);

		return new AggregatedResponse(restaurant, foods);
	}
}