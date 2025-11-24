package com.own.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.own.pojo.CreateOrderRequest;
import com.own.pojo.OrderResponse;

public interface PaypalProviderServiceInterface {

	public OrderResponse createOrderProcessor(CreateOrderRequest createOrderRequest)
			throws JsonMappingException, JsonProcessingException;

	public OrderResponse captureOrderProcessor(String orderId) throws JsonMappingException, JsonProcessingException;

}
