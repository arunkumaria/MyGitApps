package com.own.customer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.own.customer.dto.CustomerRequest;
import com.own.customer.dto.CustomerResponse;
import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;
import com.own.customer.service.CustomerService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CustomerService customerService;

	private UUID customerId;
	private CustomerRequest request;
	private CustomerResponse response;

	@BeforeEach
	void setUp() {

		customerId = UUID.randomUUID();

		request = new CustomerRequest();

		request.setName("Arun Kumar");
		request.setEmail("arun@gmail.com");
		request.setCompany("ABC Technologies");
		request.setCustomerSegment(CustomerSegment.STANDARD);
		request.setCustomerStatus(CustomerStatus.ACTIVE);

		response = new CustomerResponse();

		response.setId(customerId);
		response.setName("Arun Kumar");
		response.setEmail("arun@gmail.com");
		response.setCompany("ABC Technologies");
		response.setSegment(CustomerSegment.STANDARD);
		response.setStatus(CustomerStatus.ACTIVE);
	}

	@Test
	void createCustomer_shouldReturn201() throws Exception {

		when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(response);

		mockMvc.perform(post("/api/v1/customers").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(customerId.toString()))
				.andExpect(jsonPath("$.name").value("Arun Kumar"))
				.andExpect(jsonPath("$.email").value("arun@gmail.com"));
	}

	@Test
	void getCustomer_shouldReturn200() throws Exception {

		when(customerService.getCustomer(customerId)).thenReturn(response);

		mockMvc.perform(get("/api/v1/customers/{id}", customerId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(customerId.toString()))
				.andExpect(jsonPath("$.name").value("Arun Kumar"));
	}

	@Test
	void updateCustomer_shouldReturn200() throws Exception {

		when(customerService.updateCustomer(any(UUID.class), any(CustomerRequest.class))).thenReturn(response);

		mockMvc.perform(put("/api/v1/customers/{id}", customerId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Arun Kumar"));
	}

	@Test
	void deleteCustomer_shouldReturn204() throws Exception {

		mockMvc.perform(delete("/api/v1/customers/{id}", customerId)).andExpect(status().isNoContent());
	}
}
