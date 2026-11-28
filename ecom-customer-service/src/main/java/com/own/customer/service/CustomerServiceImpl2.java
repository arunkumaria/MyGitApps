package com.own.customer.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.own.customer.dto.CustomerEvent2;
import com.own.customer.dto.CustomerRequest2;
import com.own.customer.dto.CustomerResponse2;
import com.own.customer.entity.Customer2;
import com.own.customer.kafka.CustomerEventProducer;
import com.own.customer.kafka.CustomerEventProducer2;
import com.own.customer.repository.CustomerRepository2;

public class CustomerServiceImpl2 implements CustomerService2 {

	private final CustomerRepository2 customerRepository2;
	private final CustomerEventProducer2 customerEventProducer2;

	public CustomerServiceImpl2(CustomerRepository2 customerRepository2,
			CustomerEventProducer2 customerEventProducer2) {
		this.customerRepository2 = customerRepository2;
		this.customerEventProducer2 = customerEventProducer2;
	}

	@Override
	public CustomerResponse2 createCustomer(CustomerRequest2 customerRequest2) {

		if (customerRepository2.existsByEmail(customerRequest2.getEmail())) {
			throw new IllegalArgumentException("customer email exists" + customerRequest2.getEmail());
		}

		Customer2 customer2 = new Customer2();

		customer2.setName(customerRequest2.getName());
		customer2.setEmail(customerRequest2.getEmail());
		customer2.setCompany(customerRequest2.getCompany());
		customer2.setPhone(customerRequest2.getPhone());

		if (customerRequest2.getCustomerSegment2() != null) {
			customer2.setSegment(customerRequest2.getCustomerSegment2());
		}

		if (customerRequest2.getCustomerStatus2() != null) {
			customer2.setStatus(customerRequest2.getCustomerStatus2());
		}

		Customer2 savedCustomer2 = customerRepository2.save(customer2);

		CustomerEvent2 customerEvent2 = buildEvent(savedCustomer2, "customer-created");
		customerEventProducer2.publishCustomerCreated(customerEvent2);

		return mapToResponse(savedCustomer2);

	}

	@Override
	public CustomerResponse2 getCustomer(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CustomerResponse2> getCustomers(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerResponse2 updateCustomer(UUID id, CustomerRequest2 customerRequest2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(UUID id) {
		// TODO Auto-generated method stub

	}

	public CustomerEvent2 buildEvent(Customer2 customer2, String eventType) {

		CustomerEvent2 customerEvent2 = new CustomerEvent2();

		customerEvent2.setName(customer2.getName());
		customerEvent2.setEmail(customer2.getEmail());
		customerEvent2.setCompany(customer2.getCompany());
		customerEvent2.setCustomerSegment2(customer2.getSegment());
		customerEvent2.setCustomerStatus2(customer2.getStatus());
		customerEvent2.setId(customer2.getId());
		customerEvent2.setEventType(eventType);
		customerEvent2.setTimestamp(LocalDateTime.now());

		return customerEvent2;

	}

	public CustomerResponse2 mapToResponse(Customer2 customer2) {
		CustomerResponse2 customerResponse2 = new CustomerResponse2();

		customerResponse2.setId(customer2.getId());
		customerResponse2.setName(customer2.getName());
		customerResponse2.setEmail(customer2.getEmail());
		customerResponse2.setCustomerSegment2(customer2.getSegment());
		customerResponse2.setCustomerStatus2(customer2.getStatus());
		customerResponse2.setCreatedAt(customer2.getCreatedAt());
		customerResponse2.setUpdatedAt(customer2.getUpdatedAt());

		return customerResponse2;

	}

}
