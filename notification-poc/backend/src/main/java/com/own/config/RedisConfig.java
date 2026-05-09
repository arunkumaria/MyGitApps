//package com.own.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//
//import com.own.service.NotificationSubscriber;
//
//@Configuration
//public class RedisConfig {
//
//	@Bean
//	RedisConnectionFactory connectionFactory() {
//		return new JedisConnectionFactory();
//	}
//
//	@Bean
//	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
//			NotificationSubscriber subscriber) {
//
//		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.addMessageListener(subscriber, new PatternTopic("notifications"));
//
//		return container;
//	}
//}