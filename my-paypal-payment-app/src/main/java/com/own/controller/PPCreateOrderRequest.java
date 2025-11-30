package com.own.controller;

import lombok.Data;

@Data
public class PPCreateOrderRequest {

	private double amount;
	private String currencyCode;
	private String returnUrl;
	private String cancelUrl;

}
