package com.own.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.own.model.Restaurant;
import com.own.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public Restaurant getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Restaurant not found"));
    }
}