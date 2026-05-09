package com.own.controller;

import lombok.Data;

@Data
public class CreatePaymentRequest {

	private int userId;
	private int providerId;
	private int paymentMethodId;
	private int paymentTypeId;
	private double amount;
	private String currency;
	private String merchantTransactionReference;

}
