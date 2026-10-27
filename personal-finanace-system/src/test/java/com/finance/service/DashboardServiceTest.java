package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.finance.entity.TransactionType;
import com.finance.repository.BudgetRepository;
import com.finance.repository.TransactionRepository;
import com.finance.service.DashboardService;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

	@Mock
	TransactionRepository transactions;

	@Mock
	BudgetRepository budgets;

	@InjectMocks
	DashboardService service;

	@Test
	void calculatesMonthlyBalanceCorrectly() {
		when(transactions.sumByUserAndTypeAndDate(1L, TransactionType.INCOME, LocalDate.of(2026, 10, 1),
				LocalDate.of(2026, 10, 31))).thenReturn(new BigDecimal("40000.00"));

		when(transactions.sumByUserAndTypeAndDate(1L, TransactionType.EXPENSE, LocalDate.of(2026, 10, 1),
				LocalDate.of(2026, 10, 31))).thenReturn(new BigDecimal("19000.00"));

		when(budgets.findAllByUser_IdAndBudgetMonth(1L, LocalDate.of(2026, 10, 1))).thenReturn(List.of());

		var result = service.summary(1L, LocalDate.of(2026, 10, 25));

		assertEquals(0, new BigDecimal("40000.00").compareTo(result.totalIncome()));

		assertEquals(0, new BigDecimal("19000.00").compareTo(result.totalExpenses()));

		assertEquals(0, new BigDecimal("21000.00").compareTo(result.balance()));

		assertTrue(result.budgets().isEmpty());
	}
}