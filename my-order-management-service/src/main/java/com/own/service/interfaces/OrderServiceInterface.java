package com.own.service.interfaces;

import java.util.List;

import com.own.dto.OrderRequest;
import com.own.dto.OrderResponse;
import com.own.model.Order;

public interface OrderServiceInterface {

	public Order createService(OrderRequest orderRequest, String username);

	public List<OrderResponse> getOrderService();
}
