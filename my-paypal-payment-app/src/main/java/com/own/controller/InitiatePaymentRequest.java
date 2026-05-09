package com.own.controller;

import lombok.Data;

@Data
public class InitiatePaymentRequest {

	private String cancelUrl;
	private String successUrl;
	

}
