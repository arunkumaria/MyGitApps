package com.own.customer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerEventProducer {
	private final KafkaTemplate<String, String> kafkaTemplate;

	public CustomerEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishCustomerCreated(String customerJson) {
		kafkaTemplate.send("customer-events", customerJson);
	}
}
