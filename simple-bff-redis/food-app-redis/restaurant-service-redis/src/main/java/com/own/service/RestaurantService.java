package com.own.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.own.model.Restaurant;

@Service
public class RestaurantService {

    @Cacheable("restaurants")
    public List<Restaurant> getRestaurants() {

        System.out.println("Fetching Restaurants From DB");

        return List.of(
                new Restaurant(1L, "Dominos"),
                new Restaurant(2L, "KFC"),
                new Restaurant(3L, "McDonalds")
        );
    }
}