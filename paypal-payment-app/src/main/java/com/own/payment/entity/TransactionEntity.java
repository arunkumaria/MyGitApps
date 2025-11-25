package com.own.payment.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class TransactionEntity {

	private Integer id;
	private Integer userId;
	private Integer paymentMethodId;
	private Integer providerId;
	private Integer paymentTypeId;
	private Integer txnStatusId;
	private String txnReference;
	private String providerReference;
	private String merchantTransactionReference;
	private String currency;
	private String errorCode;
	private String errorMessage;
	private Integer retryCount;
	private BigDecimal amount;
	private Timestamp creationDate;

}