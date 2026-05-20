package com.own.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.OrderResponse;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@GetMapping("/{id}")
	public OrderResponse getOrder(@PathVariable Long id) {

		OrderResponse response = new OrderResponse();

		response.setId(id);
		response.setItemName("Burger");
		response.setQuantity(2);
		response.setStatus("CREATED");

		return response;
	}
}
