package com.own.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.own.dto.OrderRequest;
import com.own.entity.Order;
import com.own.entity.OrderItem;
import com.own.enums.OrderStatus;
import com.own.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository repository;

	@Override
	public Order createOrder(OrderRequest request) {
		var items = request.getItems().stream().map(i -> OrderItem.builder().productName(i.getProductName())
				.price(i.getPrice()).quantity(i.getQuantity()).build()).collect(Collectors.toList());

		double total = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();

		Order order = Order.builder().items(items).totalAmount(total).status(OrderStatus.PENDING).build();

		return repository.save(order);
	}

	@Override
	public Order updateStatus(Long orderId, String status) {
		Order order = repository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
		return repository.save(order);
	}

	@Override
	public Order getOrder(Long orderId) {
		return repository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
	}
}
