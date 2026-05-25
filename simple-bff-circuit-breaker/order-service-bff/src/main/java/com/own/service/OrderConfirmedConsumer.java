package com.own.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.own.model.Order;

@Service
public class OrderConfirmedConsumer {

    @KafkaListener(
            topics = "order-confirmed-topic",
            groupId = "order-group"
    )
    public void consumeConfirmedOrder(
            Order order
    ) {

        System.out.println(
                "ORDER CONFIRMED: "
                        + order.getOrderId()
        );
    }
}
