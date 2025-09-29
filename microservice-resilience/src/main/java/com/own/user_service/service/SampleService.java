package com.own.user_service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
@Service
public class SampleService {
    private static final String SAMPLE_SERVICE = "sampleService";
    @CircuitBreaker(name = SAMPLE_SERVICE, fallbackMethod = "fallbackResponse")
    public String callExternalService() {
        // Simulating a random failure
        if (Math.random() > 0.5) {
            throw new RuntimeException("Service failed");
        }
        return "Service call succeeded";
    }
    public String fallbackResponse(Exception ex) {
        return "Fallback response: " + ex.getMessage();
    }
}
