package com.own.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "restaurant-service")
public interface RestaurantClient {

    @GetMapping("/restaurants")
    List<String> restaurants();
}