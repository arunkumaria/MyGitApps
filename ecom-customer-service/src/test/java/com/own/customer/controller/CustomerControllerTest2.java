package com.own.customer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.own.customer.dto.CustomerRequest;
import com.own.customer.dto.CustomerResponse;
import com.own.customer.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest2 {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	CustomerService customerService;

	CustomerRequest customerRequest;

	CustomerResponse customerResponse;

	@Test
	public void createCustomer_shouldReturn201Created() throws Exception {

		when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(customerResponse);

		mockMvc.perform(post("/api/v1/customers").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerRequest))).andExpect(status().isCreated())
				.andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		verify(customerService.createCustomer(any(CustomerRequest.class)));
	}

}
