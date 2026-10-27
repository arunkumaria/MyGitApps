package com.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.finance.entity.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record TransactionRequest(
    @NotNull TransactionType type,
    @NotNull @DecimalMin("0.01")
    @Digits(integer = 13, fraction = 2)
    BigDecimal amount,
    @NotNull Long categoryId,
    @NotNull @PastOrPresent LocalDate transactionDate,
    @Size(max = 500) String notes
) {}
