package com.own.customer.service;

import org.springframework.stereotype.Service;

import com.own.customer.model.Customer;
import com.own.customer.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer createCustomer(Customer customer) {
        if (repository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Customer with email already exists: " + customer.getEmail());
        }
        return repository.save(customer);
    }
}

