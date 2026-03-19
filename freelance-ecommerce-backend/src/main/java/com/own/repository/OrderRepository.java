package com.own.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
}
