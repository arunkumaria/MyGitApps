package com.own.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.own.model.Order;

@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderCreatedEvent(
            Order order
    ) {

        kafkaTemplate.send(
                "order-created-topic",
                order
        );

        System.out.println(
                "ORDER CREATED EVENT SENT: "
                        + order
        );
    }
}