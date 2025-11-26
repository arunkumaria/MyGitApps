package com.own.pojo;

import lombok.Data;

@Data
public class PPCreateOrderRequest {

	private String currencyCode;
	private Double amount;
	private String returnUrl;
	private String cancelUrl;

}
