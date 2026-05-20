package com.own.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.model.Food;

public interface FoodRepository
        extends JpaRepository<Food, Long> {

    List<Food> findByRestaurantId(Long restaurantId);
}
