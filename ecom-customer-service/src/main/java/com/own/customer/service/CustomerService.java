package com.own.customer.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.own.customer.dto.CustomerRequest;
import com.own.customer.dto.CustomerResponse;

public interface CustomerService {

	CustomerResponse createCustomer(CustomerRequest customerRequest);

	CustomerResponse getCustomer(UUID id);

	Page<CustomerResponse> getCustomers(Pageable pageable);

	CustomerResponse updateCustomer(UUID id, CustomerRequest customerRequest);

	void deleteCustomer(UUID id);

}
