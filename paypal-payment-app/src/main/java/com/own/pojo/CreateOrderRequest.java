package com.own.pojo;

import lombok.Data;

@Data
public class CreateOrderRequest {

	private String currencyCode;
	private Double amount;
	private String returnUrl;
	private String cancelUrl;

}
