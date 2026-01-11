package com.own.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.OrderRequest;
import com.own.service.impl.OrderServiceImpl;
import com.own.service.interfaces.OrderServiceInterface;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final OrderServiceInterface orderServiceInterface;

	@PostMapping("/create-order")
	public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, Authentication authentication) {
		if (orderServiceInterface.createService(orderRequest, authentication.getName()) != null) {
			return ResponseEntity.ok("order created successfully");
		} else {
			return ResponseEntity.ok("order creation failed");
		}

	}

	@GetMapping("/get-order")
	public ResponseEntity<?> getOrder() {

		return ResponseEntity.ok(orderServiceInterface.getOrderService());
	}

}
