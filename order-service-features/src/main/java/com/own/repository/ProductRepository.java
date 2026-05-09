package com.own.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.entity.Customer;
import com.own.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findById(String id);}