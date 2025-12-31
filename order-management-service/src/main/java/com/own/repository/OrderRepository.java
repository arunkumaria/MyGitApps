package com.own.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUserUsername(String username);
}
