package com.own.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JsonUtil {

	private final ObjectMapper objectMapper;

	public <T> T fromJson(String json, Class<T> clazz) throws JsonMappingException, JsonProcessingException {
		return objectMapper.readValue(json, clazz);
	}

	public String toJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}
