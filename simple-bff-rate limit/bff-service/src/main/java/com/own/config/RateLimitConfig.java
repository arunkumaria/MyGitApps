package com.own.config;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Bean
    public Bucket bucket() {

        return Bucket.builder()
                .addLimit(
                        Bandwidth.builder()
                                .capacity(50)
                                .refillGreedy(50, Duration.ofMinutes(1))
                                .build()
                )
                .build();
    }
}
