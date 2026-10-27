package com.finance.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.finance.entity.TransactionType;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

class TransactionRequestTest {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	void rejectsNegativeAmount() {
		var request = new TransactionRequest(TransactionType.EXPENSE, new BigDecimal("-10.00"), 1L,
				LocalDate.of(2026, 10, 20), null);

		assertFalse(validator.validate(request).isEmpty());
	}

	@Test
	void rejectsFutureTransactionDate() {
		var request = new TransactionRequest(TransactionType.INCOME, new BigDecimal("100.00"), 1L,
				LocalDate.now().plusDays(1), null);

		assertFalse(validator.validate(request).isEmpty());
	}
}