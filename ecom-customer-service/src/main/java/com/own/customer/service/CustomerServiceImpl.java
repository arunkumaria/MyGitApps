package com.own.customer.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.own.customer.dto.CustomerEvent;
import com.own.customer.dto.CustomerRequest;
import com.own.customer.dto.CustomerResponse;
import com.own.customer.entity.Customer;
import com.own.customer.repository.CustomerRepository;

public class CustomerServiceImpl implements CustomerService {
	
	
	private final CustomerRepository customerRepository;
	
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
		
	}

	@Override
	public CustomerResponse createCustomer(CustomerRequest customerRequest) {
		if(customerRepository.existsByEmail(customerRequest.getEmail())) {
			throw new IllegalArgumentException("customer with email already exists"+ customerRequest.getEmail());
		}
		
		Customer customer = new Customer();
		customer.setName(customerRequest.getName());
		customer.setEmail(customerRequest.getEmail());
		customer.setPhone(customerRequest.getPhone());
		customer.setCompany(customerRequest.getCompany());
		
		if(customerRequest.getCustomerSegment() != null) {
			customer.setSegment(customerRequest.getCustomerSegment());
		}
		
		if(customerRequest.getCustomerStatus() != null) {
			customer.setStatus(customerRequest.getCustomerStatus());
		}
		
		Customer savedCustomer = customerRepository.save(customer);
		
		CustomerEvent event = buildEvent(savedCustomer, "CUSTOMER_CREATED");
		
		return null;
		
		
		
	}
	
	private CustomerEvent buildEvent(Customer customer, String eventType) {
		
		CustomerEvent customerEvent = new CustomerEvent();
		customerEvent.setCustomerId(customer.getId());
		customerEvent.setEventType(eventType);
		customerEvent.setName(customer.getName());
		customerEvent.setEmail(customer.getEmail());
		customerEvent.setCompany(customer.getCompany());
		customerEvent.setCustomerStatus(customer.getStatus());
		customerEvent.setCustomerSegment(customer.getSegment());
		customerEvent.setTimestamp(LocalDateTime.now());
		
		return customerEvent;
		
	}
	

	@Override
	public CustomerResponse getCustomer(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CustomerResponse> getCustomers(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerResponse updateCustomer(UUID id, CustomerRequest customerRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCustomer(UUID id) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
