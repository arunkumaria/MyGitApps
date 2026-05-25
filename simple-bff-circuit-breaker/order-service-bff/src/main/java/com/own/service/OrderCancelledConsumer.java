package com.own.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.own.model.Order;

@Service
public class OrderCancelledConsumer {

    @KafkaListener(
            topics = "order-cancelled-topic",
            groupId = "order-group"
    )
    public void consumeCancelledOrder(
            Order order
    ) {

        System.out.println(
                "ORDER CANCELLED: "
                        + order.getOrderId()
        );
    }
}