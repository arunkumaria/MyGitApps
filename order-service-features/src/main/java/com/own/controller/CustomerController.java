package com.own.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.entity.Order;
import com.own.enums.OrderStatus;
import com.own.service.OrderService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired private OrderService service;

    @GetMapping("/{id}/orders")
    public List<Order> getOrders(@PathVariable Long id,
                                @RequestParam(required = false) OrderStatus status) {

        if (status != null) {
            return service.getCustomerOrders(id)
                    .stream()
                    .filter(o -> o.getStatus() == status)
                    .toList();
        }

        return service.getCustomerOrders(id);
    }
}

