package com.own.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
