package com.own.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.own.model.Order;

@Service
public class RestaurantConsumer {

    @KafkaListener(
            topics = "order-created-topic",
            groupId = "restaurant-group"
    )
    public void consume(Order order) {

        System.out.println(
                "Restaurant validation success for order: "
                        + order.getOrderId()
        );

        /*
           Example validation logic
        */

        if (order.getRestaurantName() != null
                && !order.getRestaurantName().isEmpty()) {

            System.out.println(
                    "Restaurant exists: "
                            + order.getRestaurantName()
            );

        } else {

            System.out.println(
                    "Invalid restaurant for order: "
                            + order.getOrderId()
            );
        }
    }
}