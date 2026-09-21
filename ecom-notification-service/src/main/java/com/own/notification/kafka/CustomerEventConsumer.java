package com.own.notification.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.own.notification.dto.CustomerEvent;

@Component
public class CustomerEventConsumer {

	private static final Logger log = LoggerFactory.getLogger(CustomerEventConsumer.class);

	@KafkaListener(topics = "customer-events", groupId = "notification-service-group")
	public void consumeCustomerEvent(CustomerEvent event) {

		log.info("Received customer event. eventType={}, customerId={}", event.getEventType(), event.getCustomerId());

		switch (event.getEventType()) {

		case "CUSTOMER_CREATED" -> handleCustomerCreated(event);

		case "CUSTOMER_UPDATED" -> handleCustomerUpdated(event);

		default -> log.warn("Unknown customer event type: {}", event.getEventType());
		}
	}

	private void handleCustomerCreated(CustomerEvent event) {

		log.info("Creating welcome notification for customerId={}", event.getCustomerId());

		// Notification creation will be implemented here.
	}

	private void handleCustomerUpdated(CustomerEvent event) {

		log.info("Customer updated. customerId={}", event.getCustomerId());

		// Notification creation will be implemented here.
	}
}