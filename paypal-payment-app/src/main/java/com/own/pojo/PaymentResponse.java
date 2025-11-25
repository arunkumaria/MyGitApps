package com.own.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {

	private int txnStatusId;
	private String txnReference;
	private String providerReference;
	private String redirectUrl;

}
