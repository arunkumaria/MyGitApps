package com.own.service;

import com.own.dto.OrderRequest;
import com.own.entity.Order;
import com.own.enums.OrderStatus;

public interface OrderService {
	Order createOrder(OrderRequest request);

	Order updateStatus(Long orderId, OrderStatus status);

	Order getOrder(Long orderId);
}