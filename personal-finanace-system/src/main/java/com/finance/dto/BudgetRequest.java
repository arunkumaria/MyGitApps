package com.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public record BudgetRequest(@NotNull Long categoryId, @NotNull LocalDate month,
		@NotNull @DecimalMin("0.01") @Digits(integer = 13, fraction = 2) BigDecimal limitAmount) {
}
