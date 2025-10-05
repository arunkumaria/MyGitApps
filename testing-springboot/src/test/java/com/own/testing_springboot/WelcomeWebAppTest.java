package com.own.testing_springboot;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class WelcomeWebAppTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testWelcome() throws Exception {
        mockMvc.perform(get("/").param("name", "Geeks"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("welcome",
                        "Welcome, Geeks to the world of programming!!!"))
                .andExpect(view().name("welcome-page"))
                .andDo(print());
    }

    @Test
    void testWelcomeToEvent() throws Exception {
        mockMvc.perform(get("/event").param("name", "Geeks"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("welcomeToEvent",
                        "Welldone, Geeks You are selected to the contest!!!"))
                .andExpect(view().name("event-page"))
                .andDo(print());
    }
}
