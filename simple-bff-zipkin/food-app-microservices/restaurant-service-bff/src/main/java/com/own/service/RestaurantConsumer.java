//package com.own.service;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import com.own.model.Order;
//
//@Service
//public class RestaurantConsumer {
//
//    @KafkaListener(
//            topics = "order-created-topic",
//            groupId = "restaurant-group"
//    )
//    public void consumeOrder(Order order) {
//
//        System.out.println(
//                "RESTAURANT SERVICE RECEIVED ORDER: "
//                        + order.getOrderId()
//        );
//
//        if ("Dominos".equalsIgnoreCase(
//                order.getRestaurantName()
//        )) {
//
//            System.out.println(
//                    "Restaurant validation success for order: "
//                            + order.getOrderId()
//            );
//        }
//
//        else {
//
//            System.out.println(
//                    "Restaurant validation failed for order: "
//                            + order.getOrderId()
//            );
//        }
//    }
//}