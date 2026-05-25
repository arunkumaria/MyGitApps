package com.own.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.Order;
import com.own.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Order> placeOrder(
            @RequestBody Order order
    ) {

        System.out.println(
                "ORDER REQUEST RECEIVED: "
                        + order
        );

        Order response =
                orderService.placeOrder(order);

        System.out.println(
                "ORDER RESPONSE: "
                        + response
        );

        return ResponseEntity.ok(response);
    }
}