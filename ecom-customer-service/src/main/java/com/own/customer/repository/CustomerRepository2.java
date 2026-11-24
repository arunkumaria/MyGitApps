package com.own.customer.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.own.customer.entity.Customer2;
import com.own.customer.entity.CustomerSegment2;
import com.own.customer.entity.CustomerStatus2;

public interface CustomerRepository2 extends JpaRepository<Customer2, UUID> {

	Optional<Customer2> findByEmail(String email);

	boolean existsByEmail(String email);

	Page<Customer2> findBySegment(CustomerSegment2 customerSegment2, Pageable pageable);

	Page<Customer2> findByStatus(CustomerStatus2 customerStatus2, Pageable pageable);

	Page<Customer2> findAll(Pageable pageable);

}
