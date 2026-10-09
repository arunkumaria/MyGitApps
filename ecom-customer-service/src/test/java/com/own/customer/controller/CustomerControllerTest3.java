package com.own.customer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	@Test
	public void shouldGetCustomersSuccessfully() throws Exception {
		CustomerResponse customerResponse1 = new CustomerResponse();
		CustomerResponse customerResponse2 = new CustomerResponse();

		Page<CustomerResponse> customerPage = new PageImpl<>(List.of(customerResponse1, customerResponse2));
		when(customerService.getCustomers(any(Pageable.class))).thenReturn(customerPage);
		mockMvc.perform(get("/api/v1/customers").param("page", "0").param("size", "10").param("sortBy", "createdAt")
				.param("direction", "DESC").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isArray()).andExpect(jsonPath("$.content.length()").value(2))
				.andExpect(jsonPath("$.totalElements").value(2)).andExpect(jsonPath("$.size").value(2))
				.andExpect(jsonPath("$.number").value(0));

	}

	@Test
	public void shouldGetCustomers() throws Exception {
		Page<CustomerResponse> customerpage = new PageImpl<>(Collections.emptyList());

		when(customerService.getCustomers(any(Pageable.class))).thenReturn(customerpage);

		mockMvc.perform(get("/api/v1/customers").param("page", "0").param("size", "10").param("sortBy", "createdAt")
				.param("direction", "DESC")).andExpect(status().isOk());

		verify(customerService)
				.getCustomers(argThat(pageable -> pageable.getPageNumber() == 0 && pageable.getPageSize() == 10
						&& pageable.getSort().getOrderFor("createdAt").getDirection() == Sort.Direction.DESC));

	}

	@Test
	public void updateCustomerShouldReturn200() throws Exception {

		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setName("Arun");
		customerRequest.setPhone("1234");
		customerRequest.setEmail("arun@gmail");
		customerRequest.setCustomerStatus(CustomerStatus.ACTIVE);
		customerRequest.setCustomerSegment(CustomerSegment.PREMIUM);

		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setCompany("soft");
		customerResponse.setCreatedAt(LocalDateTime.now());
		customerResponse.setEmail("arun@gmail.com");
		UUID customerId = UUID.randomUUID();
		customerResponse.setId(customerId);
		customerResponse.setName("Arun");
		customerResponse.setPhone("1234");
		customerResponse.setSegment(CustomerSegment.PREMIUM);
		customerResponse.setStatus(CustomerStatus.ACTIVE);
		customerResponse.setUpdatedAt(LocalDateTime.now());

		when(customerService.updateCustomer(eq(customerId), any(CustomerRequest.class))).thenReturn(customerResponse);

		mockMvc.perform(put("/api/v1/customers/{id}", customerId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customerRequest)))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(customerId.toString()))
				.andExpect(jsonPath("$.email").value("arun@gmail.com"));

		verify(customerService).updateCustomer(eq(customerId), any(CustomerRequest.class));

		System.out.println("REQUEST JSON = " + objectMapper.writeValueAsString(customerRequest));

	}

	@Test
	public void deleteCustomer() throws Exception {
		final UUID customId = UUID.fromString("682cf0b9-9064-4d8d-86f4-2ec161a423a6");

		doNothing().when(customerService).deleteCustomer(customId);
		mockMvc.perform(delete("/api/v1/customers/{id}", customId)).andExpect(status().isNoContent());
		verify(customerService).deleteCustomer(customId);
		verifyNoInteractions(customerService);
	}

}
