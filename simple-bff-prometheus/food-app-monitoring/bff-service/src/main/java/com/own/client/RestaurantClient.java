package com.own.client;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "restaurant-service")
public interface RestaurantClient {

    @GetMapping("/restaurants")
    Object restaurants();
}