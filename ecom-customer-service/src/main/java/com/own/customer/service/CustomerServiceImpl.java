package com.own.customer.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.own.customer.dto.CustomerEvent;
import com.own.customer.dto.CustomerRequest;
import com.own.customer.dto.CustomerResponse;
import com.own.customer.entity.Customer;
import com.own.customer.exception.CustomerNotFoundException;
import com.own.customer.kafka.CustomerEventProducer;
import com.own.customer.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerEventProducer customerEventProducer;

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerEventProducer customerEventProducer) {
		this.customerRepository = customerRepository;
		this.customerEventProducer = customerEventProducer;

	}

	@Override
	public CustomerResponse createCustomer(CustomerRequest customerRequest) {
		if (customerRepository.existsByEmail(customerRequest.getEmail())) {
			throw new IllegalArgumentException("customer with email already exists" + customerRequest.getEmail());
		}

		Customer customer = new Customer();
		customer.setName(customerRequest.getName());
		customer.setEmail(customerRequest.getEmail());
		customer.setPhone(customerRequest.getPhone());
		customer.setCompany(customerRequest.getCompany());

		if (customerRequest.getCustomerSegment() != null) {
			customer.setSegment(customerRequest.getCustomerSegment());
		}

		if (customerRequest.getCustomerStatus() != null) {
			customer.setStatus(customerRequest.getCustomerStatus());
		}

		Customer savedCustomer = customerRepository.save(customer);

		CustomerEvent event = buildEvent(savedCustomer, "CUSTOMER_CREATED");

		customerEventProducer.publishCustomerCreated(event);

		return mapToResponse(savedCustomer);

	}

	private CustomerResponse mapToResponse(Customer customer) {

		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setId(customer.getId());
		customerResponse.setName(customer.getName());
		customerResponse.setEmail(customer.getEmail());
		customerResponse.setCompany(customer.getCompany());
		customerResponse.setPhone(customer.getPhone());
		customerResponse.setSegment(customer.getSegment());
		customerResponse.setStatus(customer.getStatus());
		customerResponse.setCreatedAt(customer.getCreatedAt());
		customerResponse.setUpdatedAt(customer.getUpdatedAt());

		return customerResponse;
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
	@Transactional(readOnly = true)
	public CustomerResponse getCustomer(UUID id) {

		Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("customer not found: "+ id));

		return mapToResponse(customer);
	}

	@Override
	public Page<CustomerResponse> getCustomers(Pageable pageable) {
		return customerRepository.findAll(pageable).map(this::mapToResponse);
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
