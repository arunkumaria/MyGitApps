package com.own.testing_springboot;


import com.own.testing_springboot.controller.*;
import com.own.testing_springboot.service.*;    
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WelcomeMvcController.class)
class WelcomeControllerSliceTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private WelcomeService welcomeService;

	@Test
	void testWelcome_Slice() throws Exception {
		when(welcomeService.greetingMessage1("Geeks")).thenReturn("Welcome, Geeks to the world of programming!!!");
		mockMvc.perform(get("/").param("name", "Geeks")).andExpect(status().isOk())
				.andExpect(model().attribute("welcome", "Welcome, Geeks to the world of programming!!!"))
				.andExpect(view().name("welcome-page"));
	}
}
