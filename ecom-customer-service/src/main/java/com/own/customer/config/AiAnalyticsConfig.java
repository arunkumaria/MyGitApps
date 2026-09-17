package com.own.customer.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AiAnalyticsProperties.class)
public class AiAnalyticsConfig {
}
