package com.own.service;

import org.springframework.stereotype.Service;

import com.own.dto.OrderRequest;
import com.own.entity.Order;
import com.own.enums.OrderStatus;
import com.own.exceptions.ResourceNotFoundException;
import com.own.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public Order createOrder(OrderRequest request) {
        double total = request.getItems()
                .stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        Order order = new Order();
        order.setItems(request.getItems());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(total);

        return repository.save(order);
    }

    public Order getOrder(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public Order updateStatus(Long id, OrderStatus status) {
        Order order = getOrder(id);
        order.setStatus(status);
        return repository.save(order);
    }

	
}