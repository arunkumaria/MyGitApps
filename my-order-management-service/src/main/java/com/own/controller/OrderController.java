package com.own.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.Order;
import com.own.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/create-order")
	public ResponseEntity<?> createOrder(@RequestBody Order order) {
		if (orderService.createService(order) != null) {
			return ResponseEntity.ok("order created successfully");
		} else {
			return ResponseEntity.ok("order creation failed");
		}

	}

	@GetMapping("/get-order")
	public ResponseEntity<?> getOrder() {

		return ResponseEntity.ok(orderService.getOrderService());
	}

}
