package com.finance.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(name = "uq_category_user_name_type", columnNames = {
		"user_id", "name", "type" }))
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false, length = 100)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private TransactionType type;

	protected Category() {
	}

	public Category(User user, String name, TransactionType type) {
		this.user = user;
		this.name = name;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getName() {
		return name;
	}

	public TransactionType getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}
}
