package com.own.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@PostMapping
	public ResponseEntity<Order> placeOrder(@RequestBody Order order) {

		Order savedOrder = orderService.placeOrder(order);

		System.out.println("RETURNING ORDER = " + savedOrder);

		return ResponseEntity.ok(savedOrder);
	}
}