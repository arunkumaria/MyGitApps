package com.own.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.OrderResponse;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public OrderResponse createOrder() {

        return new OrderResponse(101L, "ORDER_CREATED");
    }
}