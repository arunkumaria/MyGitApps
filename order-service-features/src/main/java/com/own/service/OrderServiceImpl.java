package com.own.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.own.dto.CreateOrderRequest;
import com.own.entity.Order;
import com.own.entity.OrderItem;
import com.own.entity.Product;
import com.own.enums.OrderStatus;
import com.own.exceptions.InsufficientStockException;
import com.own.exceptions.InvalidOrderStateException;
import com.own.exceptions.ResourceNotFoundException;

@Service
public class OrderServiceImpl {

	private final Map<String, Order> db = new HashMap<>();
	private final ProductService productService;

	public OrderServiceImpl(ProductService productService) {
		this.productService = productService;
	}

	public Order create(CreateOrderRequest req) {
		Order order = new Order();
		order.setItems(req.getItems());

		double total = 0;

		for (OrderItem item : req.getItems()) {
			Product p = productService.get(String.valueOf(item.getId()));

			if (p.getStock() < item.getQuantity())
				throw new InsufficientStockException("Stock too low");

			productService.reduceStock(p.getId(), item.getQuantity());
			total += p.getPrice() * item.getQuantity();
		}

		order.setTotalPrice(total);
		order.setStatus(OrderStatus.CONFIRMED);

		db.put(String.valueOf(order.getId()), order);
		return order;
	}

	public Order get(String id) {
		Order o = db.get(id);
		if (o == null)
			throw new ResourceNotFoundException("Order not found");
		return o;
	}

	public Order updateStatus(String id, OrderStatus newStatus) {
		Order order = get(id);

		// 🔥 STATE MACHINE LOGIC
		switch (order.getStatus()) {
		case PENDING -> {
			if (newStatus != OrderStatus.CONFIRMED && newStatus != OrderStatus.CANCELLED)
				throw new InvalidOrderStateException("Invalid transition");
		}
		case CONFIRMED -> {
			if (newStatus != OrderStatus.SHIPPED && newStatus != OrderStatus.CANCELLED)
				throw new InvalidOrderStateException("Invalid transition");
		}
		case SHIPPED -> {
			if (newStatus != OrderStatus.DELIVERED)
				throw new InvalidOrderStateException("Invalid transition");
		}
		default -> throw new InvalidOrderStateException("Final state reached");
		}

		order.setStatus(newStatus);
		return order;
	}
}