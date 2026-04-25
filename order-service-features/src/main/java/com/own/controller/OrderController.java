package com.own.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.OrderRequest;
import com.own.entity.Order;
import com.own.enums.OrderStatus;
import com.own.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;

	@PostMapping
	public Order createOrder(@RequestBody OrderRequest request) {
		return service.createOrder(request);
	}

	@PutMapping("/{id}/status")
	public Order updateStatus(@PathVariable Long id, @RequestParam String status) {
		
		return service.updateStatus(id, OrderStatus.PENDING);
	}

	@GetMapping("/{id}")
	public Order getOrder(@PathVariable Long id) {
		return service.getOrder(id);
	}
}
