//package com.own.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import com.own.model.Order;
//
//@Service
//public class FoodConsumer {
//
//	@Autowired
//	private KafkaTemplate<String, Object> kafkaTemplate;
//
//	@KafkaListener(topics = "order-created-topic", groupId = "food-group")
//	public void consumeOrder(Order order) {
//
//		System.out.println("FOOD SERVICE RECEIVED ORDER: " + order.getOrderId());
//
//		/*
//		 * SUCCESS CASE
//		 */
//
//		if ("Burger".equalsIgnoreCase(order.getFoodName())) {
//
//			order.setStatus("CONFIRMED");
//
//			kafkaTemplate.send("order-confirmed-topic", order);
//
//			System.out.println("Food validation success for order: " + order.getOrderId());
//		}
//
//		/*
//		 * FAILURE CASE
//		 */
//
//		else {
//
//			order.setStatus("CANCELLED");
//
//			kafkaTemplate.send("order-cancelled-topic", order);
//
//			System.out.println("Food validation failed for order: " + order.getOrderId());
//		}
//	}
//}