package com.own.customer.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.own.customer.entity.Customer;
import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;

@DataJpaTest
class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void saveCustomer_shouldSaveSuccessfully() {

		Customer customer = new Customer();

		customer.setName("Arun Kumar");
		customer.setEmail("arun@gmail.com");
		customer.setCompany("ABC Technologies");
		customer.setSegment(CustomerSegment.STANDARD);
		customer.setStatus(CustomerStatus.ACTIVE);

		Customer saved = customerRepository.save(customer);

		assertNotNull(saved.getId());

		assertEquals("Arun Kumar", saved.getName());

		assertEquals("arun@gmail.com", saved.getEmail());
	}

	@Test
	void existsByEmail_shouldReturnTrue() {

		Customer customer = new Customer();

		customer.setName("Arun Kumar");
		customer.setEmail("arun@gmail.com");
		customer.setCompany("ABC Technologies");
		customer.setSegment(CustomerSegment.STANDARD);
		customer.setStatus(CustomerStatus.ACTIVE);

		customerRepository.save(customer);

		boolean exists = customerRepository.existsByEmail("arun@gmail.com");

		assertTrue(exists);
	}

	@Test
	void findById_shouldReturnCustomer() {

		Customer customer = new Customer();

		customer.setName("Arun Kumar");
		customer.setEmail("arun@gmail.com");
		customer.setCompany("ABC Technologies");
		customer.setSegment(CustomerSegment.STANDARD);
		customer.setStatus(CustomerStatus.ACTIVE);

		Customer saved = customerRepository.save(customer);

		Optional<Customer> result = customerRepository.findById(saved.getId());

		assertTrue(result.isPresent());

		assertEquals("Arun Kumar", result.get().getName());
	}
}
