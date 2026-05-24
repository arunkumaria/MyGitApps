package com.own.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.own.model.Order;

@Service
public class OrderConsumer {

    @KafkaListener(
            topics = "order-confirmed-topic",
            groupId = "order-group"
    )
    public void consumeConfirmed(Order order) {

        System.out.println(
                "ORDER CONFIRMED: "
                        + order.getOrderId()
        );
    }

    @KafkaListener(
            topics = "order-cancelled-topic",
            groupId = "order-group"
    )
    public void consumeCancelled(Order order) {

        System.out.println(
                "ORDER CANCELLED: "
                        + order.getOrderId()
        );
    }
}
