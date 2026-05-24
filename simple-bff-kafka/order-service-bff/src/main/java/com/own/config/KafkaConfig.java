package com.own.config;

import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic orderCreatedTopic() {
        return new NewTopic(
                "order-created-topic",
                1,
                (short) 1
        );
    }

    @Bean
    public NewTopic orderConfirmedTopic() {
        return new NewTopic(
                "order-confirmed-topic",
                1,
                (short) 1
        );
    }

    @Bean
    public NewTopic orderCancelledTopic() {
        return new NewTopic(
                "order-cancelled-topic",
                1,
                (short) 1
        );
    }
}
