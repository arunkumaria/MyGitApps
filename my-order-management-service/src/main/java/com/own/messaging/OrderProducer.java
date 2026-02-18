package com.own.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	private static final String TOPIC = "order-service-topic";

	public void publishCreatedEvent(Long orderId) {

		kafkaTemplate.send(TOPIC, String.valueOf(orderId));

	}

}
