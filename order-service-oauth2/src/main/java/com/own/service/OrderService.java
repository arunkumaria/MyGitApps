package com.own.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.own.dto.OrderRequest;
import com.own.entity.Order;
import com.own.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(String email, OrderRequest request) {
        Order order = Order.builder()
                .userEmail(email)
                .productName(request.getProductName())
                .price(request.getPrice())
                .status("CREATED")
                .build();

        return orderRepository.save(order);
    }

    public List<Order> getOrders(String email) {
        return orderRepository.findByUserEmail(email);
    }
}
