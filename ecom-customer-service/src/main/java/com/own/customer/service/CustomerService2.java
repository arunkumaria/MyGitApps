package com.own.customer.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.own.customer.dto.CustomerRequest2;
import com.own.customer.dto.CustomerResponse2;

public interface CustomerService2 {
	CustomerResponse2 createCustomer(CustomerRequest2 customerRequest2);

	CustomerResponse2 getCustomer(UUID id);

	Page<CustomerResponse2> getCustomers(Pageable pageable);

	CustomerResponse2 updateCustomer(UUID id, CustomerRequest2 customerRequest2);

	void delete(UUID id);

}
