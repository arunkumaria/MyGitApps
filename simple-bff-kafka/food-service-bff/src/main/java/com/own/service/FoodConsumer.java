package com.own.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.own.model.Order;

@Service
public class FoodConsumer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public FoodConsumer(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@KafkaListener(topics = "order-created-topic", groupId = "food-group")
	public void consume(Order order) {

		if (order.getFoodName().equalsIgnoreCase("Burger") || order.getFoodName().equalsIgnoreCase("Pizza")) {

			kafkaTemplate.send("order-confirmed-topic", order);

		} else {

			order.setStatus("CANCELLED");

			kafkaTemplate.send("order-cancelled-topic", order);
		}
	}
}
