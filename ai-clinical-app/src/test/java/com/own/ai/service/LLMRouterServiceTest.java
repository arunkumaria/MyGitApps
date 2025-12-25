package com.own.ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class LLMRouterServiceTest {
    
    @Autowired
    private LLMRouterService llmRouter;
    
    @Test
    void testGenerateResponseWithOpenAI() {
        // Given
        String prompt = "What is diabetes? Answer in one sentence.";
        
        // When
        String response = llmRouter.generateResponse(prompt, "OPENAI");
        
        // Then
        assertThat(response).isNotEmpty();
        assertThat(response.toLowerCase()).contains("diabetes");
    }
    
    @Test
    void testGenerateWithContext() {
        // Given
        String query = "What medication is the patient taking?";
        List<String> context = List.of(
            "Patient prescribed Metformin 500mg twice daily",
            "Patient also taking Lisinopril 10mg for hypertension"
        );
        
        // When
        String response = llmRouter.generateWithContext(query, context, "OPENAI");
        
        // Then
        assertThat(response).isNotEmpty();
        assertThat(response.toLowerCase())
            .containsAnyOf("metformin", "lisinopril");
    }
    
    @Test
    void testProviderFallback() {
        // Given - request non-configured provider
        String prompt = "Test prompt";
        
        // When - should fallback to OpenAI
        String response = llmRouter.generateResponse(prompt, "UNKNOWN_PROVIDER");
        
        // Then - should still get a response
        assertThat(response).isNotEmpty();
    }
}
