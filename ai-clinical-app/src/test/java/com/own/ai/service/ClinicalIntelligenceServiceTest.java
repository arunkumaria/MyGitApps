package com.own.ai.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.own.ai.dto.SymptomAnalysisRequest;
import com.own.ai.dto.SymptomAnalysisResponse;

@SpringBootTest
class ClinicalIntelligenceServiceTest {
    
    @Autowired
    private ClinicalIntelligenceService clinicalService;
    
    @Test
    void testSymptomAnalysis() {
        // Given
        SymptomAnalysisRequest request = new SymptomAnalysisRequest(
            "P001",
            List.of("fever", "cough", "fatigue"),
            "Patient has history of asthma"
        );
        
        // When
        SymptomAnalysisResponse response = clinicalService.analyzeSymptoms(request);
        
        // Then
        assertThat(response.potentialDiagnoses()).isNotEmpty();
        assertThat(response.recommendedTests()).isNotEmpty();
        assertThat(response.urgencyLevel()).isIn("ROUTINE", "URGENT", "EMERGENT");
        assertThat(response.reasoning()).isNotEmpty();
    }
    
    @Test
    void testEmergentSymptoms() {
        // Given
        SymptomAnalysisRequest request = new SymptomAnalysisRequest(
            "P002",
            List.of("chest pain", "shortness of breath", "sweating"),
            "Patient has history of hypertension"
        );
        
        // When
        SymptomAnalysisResponse response = clinicalService.analyzeSymptoms(request);
        
        // Then
        assertThat(response.urgencyLevel()).isEqualTo("EMERGENT");
        assertThat(response.potentialDiagnoses()).isNotEmpty();
    }
}
