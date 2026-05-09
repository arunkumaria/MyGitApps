package com.own.config;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

@Component
public class RateLimitConfig {

	private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

	public Bucket resolveBucket(String key) {
		return cache.computeIfAbsent(key, this::newBucket);
	}

	private Bucket newBucket(String key) {
		Refill refill = Refill.greedy(5, Duration.ofHours(1)); // 5 requests per hour
		Bandwidth limit = Bandwidth.classic(5, refill);
		return Bucket4j.builder().addLimit(limit).build();
	}
}