package com.own.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.entity.Order;
import com.own.entity.OrderItem;
import com.own.enums.OrderStatus;
import com.own.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired private OrderService service;

    @PostMapping("/{customerId}")
    public Order createOrder(@PathVariable Long customerId,
                             @RequestBody List<OrderItem> items) {
        return service.createOrder(customerId, items);
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id,
                             @RequestParam OrderStatus status) {
        return service.updateStatus(id, status);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return service.getOrder(id);
    }
}