package com.own.service;

import com.own.dto.OrderRequest;
import com.own.entity.Order;

public interface OrderService {
	Order createOrder(OrderRequest request);

	Order updateStatus(Long orderId, String status);

	Order getOrder(Long orderId);
}