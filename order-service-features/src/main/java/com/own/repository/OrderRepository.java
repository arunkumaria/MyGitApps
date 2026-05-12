package com.own.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.entity.Order;
import com.own.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByCustomerIdAndOrderStatus(Long customerId,
                                               OrderStatus status);
}