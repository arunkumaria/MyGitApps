package com.own.customer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerEventConsumer {

	@KafkaListener(topics = "customer-events", groupId = "customer-service-group")
	public void consume(String message) {
		System.out.println("Received customer event: " + message);
		// Process event (e.g., update DB, trigger workflow)
	}
}
