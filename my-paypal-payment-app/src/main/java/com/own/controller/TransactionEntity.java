package com.own.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class TransactionEntity {
	
	private Integer id;
	private Integer userId;
	private Integer txnStatusId;
	private Integer providerId;
	private Integer paymentMethodId;
	private Integer paymentTypeId;
	private Integer retryCount;
	private BigDecimal amount;
	private String currency;
	private String txnReference;
	private String providerReference;
	private String merchantTransactionReference;
	private String errorCode;
	private String errorMessage;
	private Timestamp creationDate;
	
	
	

}
