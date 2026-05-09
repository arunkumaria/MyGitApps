package com.own.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}