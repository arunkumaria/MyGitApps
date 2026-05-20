package com.own.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.own.model.Food;
import com.own.repository.FoodRepository;

@Service
public class FoodService {

	private final FoodRepository repository;

	public FoodService(FoodRepository repository) {
		this.repository = repository;
	}

	public Food create(Food food) {
		return repository.save(food);
	}

	public List<Food> getFoods(Long restaurantId) {
		return repository.findByRestaurantId(restaurantId);
	}
}
