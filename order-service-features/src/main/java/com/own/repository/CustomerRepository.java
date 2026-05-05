package com.own.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}