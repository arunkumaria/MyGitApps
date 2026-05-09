package com.own;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.own.service.OrderService;

@SpringBootTest
class OrderServiceTest {

	@Autowired
	OrderService service;

	@Test
	void testCreateOrder() {
		assertNotNull(service);
	}
}
