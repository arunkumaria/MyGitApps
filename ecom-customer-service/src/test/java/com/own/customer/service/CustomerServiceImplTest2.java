package com.own.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.own.customer.dto.CustomerEvent;
import com.own.customer.dto.CustomerRequest;
import com.own.customer.dto.CustomerResponse;
import com.own.customer.entity.Customer;
import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;
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

	@Test
	void getCustomer() {
		UUID id = UUID.randomUUID();
		Customer customer = new Customer();
		customer.setCompany("Upwork");
		customer.setEmail("arun@gmail.com");
		customer.setId(id);
		customer.setName("Arun");
		customer.setPhone("8553253334");
		customer.setSegment(CustomerSegment.STANDARD);
		customer.setStatus(CustomerStatus.ACTIVE);

		when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
		CustomerResponse customerResponse = customerService.getCustomer(id);

		assertNotNull(customerResponse);
		assertEquals(id, customerResponse.getId());
		assertEquals("Arun", customerResponse.getName());
		assertEquals("arun@gmail.com", customerResponse.getEmail());
		verify(customerRepository, times(1)).findById(id);
	}

	@Test
	void getCustomers() {
		Pageable pageable = (Pageable) PageRequest.of(0, 10);
		UUID customerId = UUID.fromString("3a88ac1c-6a32-4703-a6bc-5f8b90d61e22");
		Customer customer = new Customer();
		customer.setCompany("Upwork");
		customer.setEmail("arun@upwork.com");
		customer.setId(customerId);
		customer.setName("Arun");
		customer.setPhone("8553253334");
		customer.setSegment(CustomerSegment.STANDARD);
		customer.setStatus(CustomerStatus.ACTIVE);

		Page<Customer> customerPage = new PageImpl<>(List.of(customer), pageable, 1);

		when(customerRepository.findAll(pageable)).thenReturn(customerPage);

		Page<CustomerResponse> result = customerService.getCustomers(pageable);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(1, result.getContent().size());

		CustomerResponse customerResponse = result.getContent().get(0);

		assertEquals(customerId, result.getContent().get(0).getId());
		assertEquals("Arun", customerResponse.getName());
		assertEquals("arun@upwork.com", customerResponse.getEmail());

		verify(customerRepository, times(1)).findAll(pageable);
	}
}
