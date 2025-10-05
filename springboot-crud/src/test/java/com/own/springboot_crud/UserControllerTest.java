package com.own.springboot_crud;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldPostUser() throws Exception {
		User2 user2 = new User2();
		user2.setName("Arun");
		user2.setEmail("arun@gmail.com");

		mockMvc.perform(
				post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user2)))
				.andExpect((jsonPath("$.name")).value("Arun"))
				.andExpect(jsonPath("$.email").value("arun@gmail.com"));

	}

	@Test
	void shouldPostUser2() throws Exception {

		mockMvc.perform(get("/users")).andExpect(status().isOk());

		

	}

}
