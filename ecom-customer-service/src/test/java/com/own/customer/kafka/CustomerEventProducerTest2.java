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
import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;

@ExtendWith(MockitoExtension.class)
public class CustomerEventProducerTest2 {

	@Mock
	private KafkaTemplate<String, CustomerEvent> kafkaTemplate;

	@InjectMocks
	private CustomerEventProducer customerEventProducer;

	@Test
	void customer_creation_event() {

		CustomerEvent customerEvent = new CustomerEvent();

		UUID customerId = UUID.randomUUID();

		customerEvent.setCompany("upwork");
		customerEvent.setCustomerSegment(CustomerSegment.STANDARD);
		customerEvent.setCustomerStatus(CustomerStatus.ACTIVE);
		customerEvent.setEmail("arun@upwork.com");
		customerEvent.setEventType("customer-created");
		customerEvent.setName("Arun");
		customerEvent.setCustomerId(customerId);

		customerEventProducer.publishCustomerCreated(customerEvent);

		verify(kafkaTemplate).send(eq("customer-created"), eq(customerId.toString()), any(CustomerEvent.class));

	}

}
