package com.own.customer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.own.customer.dto.CustomerEvent;
import com.own.customer.dto.CustomerRequest;
import com.own.customer.dto.CustomerResponse;
import com.own.customer.entity.Customer;
import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;
import com.own.customer.exception.CustomerNotFoundException;
import com.own.customer.kafka.CustomerEventProducer;
import com.own.customer.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest2 {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CustomerEventProducer customerEventProducer;

	@InjectMocks
	private CustomerServiceImpl customerService;

	private CustomerRequest customerRequest;

	@BeforeEach
	void setUp() {
		customerRequest = new CustomerRequest();
		customerRequest.setEmail("arun@gmail.com");
		customerRequest.setPhone("8553253334");
		customerRequest.setName("Arun");
		customerRequest.setCompany("Upwork");
		customerRequest.setCustomerStatus(CustomerStatus.ACTIVE);
		customerRequest.setCustomerSegment(CustomerSegment.STANDARD);

	}

	@Test
	void createCustomer_success() {
		when(customerRepository.existsByEmail("arun@gmail.com")).thenReturn(false);
		when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
			Customer customer = invocation.getArgument(0);
			customer.setId(UUID.randomUUID());
			return customer;
		});
		CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
		assertNotNull(customerResponse);
		assertEquals("Arun", customerResponse.getName());
		assertEquals("arun@gmail.com", customerResponse.getEmail());
		verify(customerRepository).existsByEmail("arun@gmail.com");
		verify(customerRepository).save(any(Customer.class));
		verify(customerEventProducer).publishCustomerCreated(any(CustomerEvent.class));

	}
}
