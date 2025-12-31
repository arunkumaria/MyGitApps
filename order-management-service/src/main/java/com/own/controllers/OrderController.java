package com.own.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.OrderRequest;
import com.own.entity.Order;
import com.own.service.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Order APIs")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public Order create(@RequestBody OrderRequest request, Authentication authentication) {
		return orderService.createOrder(request, authentication.getName());
	}

	@GetMapping
	public List<Order> list(Authentication authentication) {
		return orderService.getOrders(authentication.getName());
	}
}
