package com.own.test_rest_template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestRestTemplateApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void testSayHello() {
		ResponseEntity<String> response = testRestTemplate.getForEntity("/api/hello", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello, World!", response.getBody());
	}
}
