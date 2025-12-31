package com.own.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	private static final String TOPIC = "order-created-topic";

	public void publishOrderCreatedEvent(Long orderId) {
		kafkaTemplate.send(TOPIC, String.valueOf(orderId));
	}
}
