package com.own.customer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.own.customer.dto.CustomerRequest;
import com.own.customer.dto.CustomerResponse;
import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;
import com.own.customer.service.CustomerService;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest3 {

	@MockitoBean
	private CustomerService customerService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void create_customer_201_created() throws JsonProcessingException, Exception {

		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setName("Arun");
		customerRequest.setPhone("1234");
		customerRequest.setEmail("arun@gmail");
		customerRequest.setCustomerStatus(CustomerStatus.ACTIVE);
		customerRequest.setCustomerSegment(CustomerSegment.PREMIUM);

		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setCompany("soft");
		customerResponse.setCreatedAt(LocalDateTime.now());
		customerResponse.setEmail("arun@gmail");
		UUID customerId = UUID.randomUUID();
		customerResponse.setId(customerId);
		customerResponse.setName("Arun");
		customerResponse.setPhone("1234");
		customerResponse.setSegment(CustomerSegment.PREMIUM);
		customerResponse.setStatus(CustomerStatus.ACTIVE);
		customerResponse.setUpdatedAt(LocalDateTime.now());

		when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(customerResponse);

		mockMvc.perform(post("/api/v1/customers").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerRequest))).andDo(print())
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(customerId.toString()))
				.andExpect(jsonPath("$.name").value("Arun")).andExpect(jsonPath("$.email").value("arun@gmail"));

		verify(customerService).createCustomer(any(CustomerRequest.class));

	}

}
