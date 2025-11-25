package com.own.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class TransactionDto {
	private Integer id;
	private Integer userId;
	private Integer providerId;
	private Integer paymentMethodId;
	private Integer paymentTypeId;
	private BigDecimal amount;
	private String currency;
	private String merchantTransactionReference;
	private String txnReference;
	private String providerReference;
	private Integer txnStatusId;
	private String errorCode;
	private String errorMessage;
	private Timestamp creationDate;
	private Integer retryCount;

}
