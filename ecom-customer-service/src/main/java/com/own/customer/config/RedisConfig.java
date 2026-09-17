package com.own.customer.config;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.own.customer.dto.CustomerResponse;

@Configuration
@EnableCaching
public class RedisConfig {

	@Bean
	public ObjectMapper redisObjectMapper() {

		return JsonMapper.builder().addModule(new JavaTimeModule()).build();
	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,
			ObjectMapper redisObjectMapper) {

		Jackson2JsonRedisSerializer<CustomerResponse> customerSerializer = new Jackson2JsonRedisSerializer<>(
				redisObjectMapper, CustomerResponse.class);

		RedisCacheConfiguration customerCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(10))
				.serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(customerSerializer));

		return RedisCacheManager.builder(redisConnectionFactory)
				.withCacheConfiguration("customers", customerCacheConfiguration).build();
	}
}