package com.own.transaction_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.own.transaction_management.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
