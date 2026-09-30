package com.own.customer.kafka;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import com.own.customer.dto.CustomerEvent;

@ExtendWith(MockitoExtension.class)
class CustomerEventProducerTest {

	@Mock
	private KafkaTemplate<String, CustomerEvent> kafkaTemplate;

	@InjectMocks
	private CustomerEventProducer customerEventProducer;

	@Test
	void publishCustomerCreated_shouldSendKafkaMessage() {

		CustomerEvent event = new CustomerEvent();

		UUID customerId = UUID.randomUUID();

		event.setCustomerId(customerId);
		event.setEventType("CUSTOMER_CREATED");
		event.setName("Arun Kumar");
		event.setEmail("arun@gmail.com");

		customerEventProducer.publishCustomerCreated(event);

		verify(kafkaTemplate).send(eq("customer-created"), eq(customerId.toString()), any(CustomerEvent.class));
	}
}