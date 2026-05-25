package com.own.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.own.model.Order;

@Service
public class OrderService {

	@Autowired
	private OrderProducer orderProducer;

	public Order placeOrder(Order order) {

		order.setStatus("PENDING");

		orderProducer.sendOrderCreatedEvent(order);

		return order;
	}
}
