package com.own.dto;

import java.util.List;

public class AggregatedResponse {

	private RestaurantDTO restaurant;
	private List<FoodDTO> foods;

	public AggregatedResponse() {
	}

	public AggregatedResponse(RestaurantDTO restaurant, List<FoodDTO> foods) {

		this.restaurant = restaurant;
		this.foods = foods;
	}

	public RestaurantDTO getRestaurant() {
		return restaurant;
	}

	public List<FoodDTO> getFoods() {
		return foods;
	}

	public void setRestaurant(RestaurantDTO restaurant) {
		this.restaurant = restaurant;
	}

	public void setFoods(List<FoodDTO> foods) {
		this.foods = foods;
	}
}
