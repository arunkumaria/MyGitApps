package com.own.customer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.own.customer.entity.Customer;
import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;

@DataJpaTest
public class CustomerRepositoryTest2 {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void customer_create() {

		Customer customer = new Customer();
		customer.setCompany("upwork");
		customer.setEmail("arun@upwork.com");
		customer.setName("Arun");
		customer.setPhone("8553253334");
		customer.setSegment(CustomerSegment.STANDARD);
		customer.setStatus(CustomerStatus.ACTIVE);

		Customer saved = customerRepository.save(customer);

		assertNotNull(saved.getId());
		assertEquals("Arun", saved.getName());
		assertEquals("arun@upwork.com", saved.getEmail());

	}

	@Test
	void customer_existsByEmail() {

		Customer customer = new Customer();
		customer.setCompany("Upwork");
		customer.setEmail("arun@upwork.com");
		customer.setName("Arun");
		customer.setPhone("8553253334");
		customer.setSegment(CustomerSegment.STANDARD);
		customer.setStatus(CustomerStatus.ACTIVE);
		
		customerRepository.save(customer);

		boolean exists = customerRepository.existsByEmail("arun@upwork.com");

		assertTrue(exists);
	}

}
