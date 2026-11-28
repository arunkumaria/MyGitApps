package com.own.customer.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.own.customer.dto.CustomerEvent;
import com.own.customer.dto.CustomerEvent2;

@Component
public class CustomerEventProducer2 {

	private String CUSTOMER_CREATED_TOPIC = "customer-created";
	private String CUSTOMER_UPDATED_TOPIC = "customer-updated";
	private KafkaTemplate<String, CustomerEvent2> kafkaTemplate;

	public void publishCustomerCreated(CustomerEvent2 customerEvent2) {
		kafkaTemplate.send(CUSTOMER_CREATED_TOPIC, customerEvent2.getId().toString(), customerEvent2);
	}

	public void publishCustomerUpdated(CustomerEvent2 customerEvent2) {
		kafkaTemplate.send(CUSTOMER_UPDATED_TOPIC, customerEvent2.getId().toString(), customerEvent2);

	}

}
