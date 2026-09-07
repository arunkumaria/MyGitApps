package com.own.customer.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.own.customer.entity.Customer;
import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

	Optional<Customer> findByEmail(String email);

	boolean existsByEmail(String email);

	Page<Customer> findBySegment(CustomerSegment customerSegment, Pageable page);

	Page<Customer> findByStatus(CustomerStatus customerStatus, Pageable page);

}
