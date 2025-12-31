package com.own.service;

import java.util.List;

import com.own.dto.OrderRequest;
import com.own.entity.Order;

public interface OrderService {
	Order createOrder(OrderRequest request, String username);

	List<Order> getOrders(String username);
}
