package com.own.customer.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
