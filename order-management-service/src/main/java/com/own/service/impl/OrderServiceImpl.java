package com.own.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.own.dto.OrderRequest;
import com.own.entity.Order;
import com.own.entity.User;
import com.own.exception.ResourceNotFoundException;
import com.own.messaging.OrderEventProducer;
import com.own.repository.OrderRepository;
import com.own.repository.UserRepository;
import com.own.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final OrderEventProducer orderEventProducer;

	@Override
	public Order createOrder(OrderRequest request, String username) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Order order = Order.builder().productName(request.getProductName()).quantity(request.getQuantity())
				.price(request.getPrice()).user(user).build();

		Order savedOrder = orderRepository.save(order);

		// 🔥 Publish Kafka Event
		orderEventProducer.publishOrderCreatedEvent(savedOrder.getId());

		return savedOrder;
	}

	@Override
	public List<Order> getOrders(String username) {
		return orderRepository.findByUserUsername(username);
	}
}
