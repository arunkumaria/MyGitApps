package com.own.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.entity.Order;
import com.own.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired private OrderRepository repo;

    @PostMapping
    public Order create(@RequestBody Order o) {
        return repo.save(o);
    }
}
