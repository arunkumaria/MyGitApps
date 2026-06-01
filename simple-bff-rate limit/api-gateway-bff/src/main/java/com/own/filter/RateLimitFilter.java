package com.own.filter;


import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Component
public class RateLimitFilter
        extends AbstractGatewayFilterFactory<RateLimitFilter.Config> {

    private final Map<String, Bucket> buckets =
            new ConcurrentHashMap<>();

    public RateLimitFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            String ip =
                    exchange.getRequest()
                            .getRemoteAddress()
                            .getAddress()
                            .getHostAddress();

            Bucket bucket =
                    buckets.computeIfAbsent(
                            ip,
                            this::createBucket
                    );

            if (bucket.tryConsume(1)) {
                return chain.filter(exchange);
            }

            exchange.getResponse()
                    .setStatusCode(
                            HttpStatus.TOO_MANY_REQUESTS
                    );

            return exchange.getResponse().setComplete();
        };
    }

    private Bucket createBucket(String ip) {

        return Bucket.builder()
                .addLimit(
                        Bandwidth.classic(
                                20,
                                Refill.greedy(
                                        20,
                                        Duration.ofMinutes(1)
                                )
                        )
                )
                .build();
    }

    public static class Config {
    }
}