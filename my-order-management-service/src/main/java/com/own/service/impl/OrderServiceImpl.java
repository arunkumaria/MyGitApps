package com.own.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.own.dto.AuthResponse;
import com.own.dto.OrderRequest;
import com.own.dto.OrderResponse;
import com.own.messaging.OrderProducer;
import com.own.model.Order;
import com.own.model.User;
import com.own.repository.OrderRepository;
import com.own.repository.UserRepository;
import com.own.service.interfaces.OrderServiceInterface;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderServiceInterface {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final OrderProducer orderProducer;

	public Order createService(OrderRequest orderRequest, String username) {

		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("user not found"));
		Order temp = Order.builder().productName(orderRequest.getProductName()).quantity(orderRequest.getQuantity())
				.price(orderRequest.getPrice()).user(user).build();

		Order savedOrder = orderRepository.save(temp);
		orderProducer.publishCreatedEvent(savedOrder.getId());
		return savedOrder;
	}

	public List<OrderResponse> getOrderService() {

		List<Order> list = orderRepository.findAll();
		List<OrderResponse> listOrRes = new ArrayList<>();

		for (Order l : list) {
			AuthResponse authResponse = AuthResponse.builder().username(l.getUser().getUsername())
					.mobile(l.getUser().getMobile()).build();

			OrderResponse orderResponse = OrderResponse.builder().id(l.getId()).productName(l.getProductName())
					.quantity(l.getQuantity()).price(l.getPrice()).authResponse(authResponse).build();
			listOrRes.add(orderResponse);
		}

		return listOrRes;

	}

}
