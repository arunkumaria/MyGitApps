package com.finance.service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.entity.TransactionType;
import com.finance.repository.BudgetRepository;
import com.finance.repository.TransactionRepository;

@Service
@Transactional(readOnly = true)
public class DashboardService {

	private final TransactionRepository transactions;
	private final BudgetRepository budgets;

	public DashboardService(TransactionRepository transactions, BudgetRepository budgets) {
		this.transactions = transactions;
		this.budgets = budgets;
	}

	public DashboardSummary summary(Long userId, LocalDate selectedMonth) {
		LocalDate month = selectedMonth.withDayOfMonth(1);
		LocalDate end = month.withDayOfMonth(month.lengthOfMonth());

		BigDecimal income = transactions.sumByUserAndTypeAndDate(userId, TransactionType.INCOME, month, end);

		BigDecimal expenses = transactions.sumByUserAndTypeAndDate(userId, TransactionType.EXPENSE, month, end);

		List<BudgetStatus> statuses = budgets.findAllByUser_IdAndBudgetMonth(userId, month).stream().map(budget -> {
			BigDecimal spent = transactions.sumByUserAndCategoryAndTypeAndDate(userId, budget.getCategory().getId(),
					TransactionType.EXPENSE, month, end);

			BigDecimal remaining = budget.getLimitAmount().subtract(spent);

			BigDecimal percentage = spent.multiply(new BigDecimal("100")).divide(budget.getLimitAmount(), 2,
					java.math.RoundingMode.HALF_UP);

			String status = percentage.compareTo(new BigDecimal("100")) >= 0 ? "EXCEEDED"
					: percentage.compareTo(new BigDecimal("80")) >= 0 ? "NEAR_LIMIT" : "WITHIN_BUDGET";

			return new BudgetStatus(budget.getCategory().getName(), budget.getLimitAmount(), spent, remaining,
					percentage, status);
		}).toList();

		return new DashboardSummary(month, income, expenses, income.subtract(expenses), statuses);
	}

	public record DashboardSummary(LocalDate month, BigDecimal totalIncome, BigDecimal totalExpenses,
			BigDecimal balance, List<BudgetStatus> budgets) {
	}

	public record BudgetStatus(String category, BigDecimal budgetLimit, BigDecimal spent, BigDecimal remaining,
			BigDecimal utilizationPercent, String status) {
	}
}
