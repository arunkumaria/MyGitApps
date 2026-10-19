package com.own.customer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.own.customer.entity.Customer;
import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;

@DataJpaTest
public class CustomerRepositoryTest2 {

	private CustomerRepository customerRepository;

	@Test
	public void cutomer_create() {

		Customer customer = new Customer();
		customer.setCompany("upwork");
		customer.setId(UUID.randomUUID());
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

}
