package com.own.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.own.model.Order;
import com.own.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;

	public Order createService(Order order) {

		Order temp = new Order();
		temp.setProductName(order.getProductName());
		temp.setQuantity(order.getQuantity());
		temp.setPrice(order.getPrice());

		return orderRepository.save(temp);
	}

	public List<Order> getOrderService() {
		return orderRepository.findAll();

	}

}
