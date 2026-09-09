package com.own.customer.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.own.customer.dto.CustomerEvent;

@Component
public class CustomerEventProducer {
	private static final String CUSTOMER_CREATED_TOPIC = "customer-created";
	private static final String CUSTOMER_UPDATED_TOPIC = "customer-updated";
	private final KafkaTemplate<String, CustomerEvent> kafkaTemplate;

	public CustomerEventProducer(KafkaTemplate<String, CustomerEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishCustomerCreated(CustomerEvent customerEvent) {
		kafkaTemplate.send(CUSTOMER_CREATED_TOPIC, customerEvent.getCustomerId().toString(), customerEvent);
	}
	
	public void publishCustomerUpdated(CustomerEvent customerEvent) {
		kafkaTemplate.send(CUSTOMER_UPDATED_TOPIC, customerEvent.getCustomerId().toString(), customerEvent);
	}

}
