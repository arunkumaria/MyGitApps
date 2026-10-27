package com.finance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "budgets", uniqueConstraints = @UniqueConstraint(name = "uq_budget_user_category_month", columnNames = {
		"user_id", "category_id", "budget_month" }))
public class Budget {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "budget_month", nullable = false)
	private LocalDate budgetMonth;

	@Column(name = "limit_amount", nullable = false, precision = 15, scale = 2)
	private BigDecimal limitAmount;

	protected Budget() {
	}

	public Budget(User user, Category category, LocalDate budgetMonth, BigDecimal limitAmount) {
		this.user = user;
		this.category = category;
		this.budgetMonth = budgetMonth;
		this.limitAmount = limitAmount;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Category getCategory() {
		return category;
	}

	public LocalDate getBudgetMonth() {
		return budgetMonth;
	}

	public BigDecimal getLimitAmount() {
		return limitAmount;
	}

	public void update(BigDecimal amount) {
		this.limitAmount = amount;
	}
}
