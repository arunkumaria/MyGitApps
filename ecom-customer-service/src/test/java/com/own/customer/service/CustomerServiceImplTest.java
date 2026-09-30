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

import com.own.customer.dto.CustomerRequest;
import com.own.customer.dto.CustomerResponse;
import com.own.customer.entity.Customer;
import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;
import com.own.customer.exception.CustomerNotFoundException;
import com.own.customer.kafka.CustomerEventProducer;
import com.own.customer.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CustomerEventProducer customerEventProducer;

	@InjectMocks
	private CustomerServiceImpl customerService;

	private UUID customerId;
	private Customer customer;
	private CustomerRequest customerRequest;

	@BeforeEach
	void setUp() {

		customerId = UUID.randomUUID();

		customerRequest = new CustomerRequest();

		customerRequest.setName("Arun Kumar");
		customerRequest.setEmail("arun@gmail.com");
		customerRequest.setCompany("ABC Technologies");
		customerRequest.setCustomerSegment(CustomerSegment.STANDARD);
		customerRequest.setCustomerStatus(CustomerStatus.ACTIVE);

		customer = new Customer();

		customer.setId(customerId);
		customer.setName("Arun Kumar");
		customer.setEmail("arun@gmail.com");
		customer.setCompany("ABC Technologies");
		customer.setSegment(CustomerSegment.STANDARD);
		customer.setStatus(CustomerStatus.ACTIVE);
	}

	@Test
	void createCustomer_shouldCreateCustomerSuccessfully() {

		when(customerRepository.existsByEmail("arun@gmail.com")).thenReturn(false);

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		CustomerResponse response = customerService.createCustomer(customerRequest);

		assertNotNull(response);

		assertEquals(customerId, response.getId());
		assertEquals("Arun Kumar", response.getName());
		assertEquals("arun@gmail.com", response.getEmail());

		verify(customerRepository).existsByEmail("arun@gmail.com");

		verify(customerRepository).save(any(Customer.class));

		verify(customerEventProducer).publishCustomerCreated(any());
	}

	@Test
	void createCustomer_shouldThrowExceptionWhenEmailAlreadyExists() {

		when(customerRepository.existsByEmail("arun@gmail.com")).thenReturn(true);

		assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(customerRequest));

		verify(customerRepository).existsByEmail("arun@gmail.com");

		verify(customerRepository, never()).save(any(Customer.class));

		verify(customerEventProducer, never()).publishCustomerCreated(any());
	}

	@Test
	void getCustomer_shouldReturnCustomerSuccessfully() {

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

		CustomerResponse response = customerService.getCustomer(customerId);

		assertNotNull(response);

		assertEquals(customerId, response.getId());
		assertEquals("Arun Kumar", response.getName());
		assertEquals("arun@gmail.com", response.getEmail());

		verify(customerRepository).findById(customerId);
	}

	@Test
	void getCustomer_shouldThrowExceptionWhenCustomerDoesNotExist() {

		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(customerId));

		verify(customerRepository).findById(customerId);
	}

	@Test
	void updateCustomer_shouldUpdateCustomerSuccessfully() {

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		CustomerResponse response = customerService.updateCustomer(customerId, customerRequest);

		assertNotNull(response);

		assertEquals(customerId, response.getId());
		assertEquals("Arun Kumar", response.getName());

		verify(customerRepository).findById(customerId);

		verify(customerRepository).save(customer);
	}

	@Test
	void updateCustomer_shouldThrowExceptionWhenCustomerDoesNotExist() {

		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		assertThrows(CustomerNotFoundException.class,
				() -> customerService.updateCustomer(customerId, customerRequest));

		verify(customerRepository, never()).save(any(Customer.class));
	}

	@Test
	void deleteCustomer_shouldDeleteCustomerSuccessfully() {

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

		customerService.deleteCustomer(customerId);

		verify(customerRepository).findById(customerId);

		verify(customerRepository).delete(customer);
	}

	@Test
	void deleteCustomer_shouldThrowExceptionWhenCustomerDoesNotExist() {

		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(customerId));

		verify(customerRepository, never()).delete(any(Customer.class));
	}
}
