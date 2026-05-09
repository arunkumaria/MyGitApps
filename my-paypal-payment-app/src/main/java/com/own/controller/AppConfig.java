package com.own.controller;

import java.io.IOException;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;

import org.apache.hc.core5.util.TimeValue;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

	@Bean
	RestClient restClient(RestClient.Builder builder) {

		// pool
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		poolingHttpClientConnectionManager.setMaxTotal(100);
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100);

		// closable
		CloseableHttpClient closeableHttpClient = HttpClients.custom()
				.setConnectionManager(poolingHttpClientConnectionManager).evictIdleConnections(TimeValue.ofSeconds(30))
				.build();

		// component
		HttpComponentsClientHttpRequestFactory componentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				closeableHttpClient);
		componentsClientHttpRequestFactory.setConnectionRequestTimeout(10000);
		componentsClientHttpRequestFactory.setConnectTimeout(10000);
		componentsClientHttpRequestFactory.setReadTimeout(15000);

		// builder

		return builder.requestFactory(componentsClientHttpRequestFactory).build();

	}

	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);

		return modelMapper;

	}

}
