package com.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.finance.entity.Transaction;
import com.finance.entity.TransactionType;

public record TransactionResponse(Long id, String category, Long categoryId, TransactionType type, BigDecimal amount,
		LocalDate transactionDate, String notes) {
	public static TransactionResponse from(Transaction tx) {
		return new TransactionResponse(tx.getId(), tx.getCategory().getName(), tx.getCategory().getId(), tx.getType(),
				tx.getAmount(), tx.getTransactionDate(), tx.getNotes());
	}
}
