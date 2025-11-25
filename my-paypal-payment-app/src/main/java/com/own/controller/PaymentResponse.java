package com.own.controller;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {

	private String txnReference;
	private String providerReference;
	private String redrectUrl;
	private int txnStatusId;

}
