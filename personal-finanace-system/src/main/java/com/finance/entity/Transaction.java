package com.finance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions", indexes = @Index(name = "idx_transaction_user_date", columnList = "user_id, transaction_date"))
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private TransactionType type;

	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal amount;

	@Column(name = "transaction_date", nullable = false)
	private LocalDate transactionDate;

	@Column(length = 500)
	private String notes;

	protected Transaction() {
	}

	public Transaction(User user, Category category, TransactionType type, BigDecimal amount, LocalDate transactionDate,
			String notes) {
		this.user = user;
		this.category = category;
		this.type = type;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.notes = notes;
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

	public TransactionType getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public String getNotes() {
		return notes;
	}

	public void update(Category category, TransactionType type, BigDecimal amount, LocalDate date, String notes) {
		this.category = category;
		this.type = type;
		this.amount = amount;
		this.transactionDate = date;
		this.notes = notes;
	}
}