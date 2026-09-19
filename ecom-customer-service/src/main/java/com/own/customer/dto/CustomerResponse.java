package com.own.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Customer response")
public class CustomerResponse {

	@Schema(description = "Unique customer UUID", example = "682cf0b9-9064-4d8d-86f4-2ec161a423a6")
	private UUID id;

	@Schema(description = "Customer name", example = "Arun Kumar")
	private String name;

	@Schema(description = "Customer email", example = "arun@example.com")
	private String email;

	@Schema(description = "Customer company", example = "ABC Technologies")
	private String company;

	@Schema(description = "Customer segment", example = "PREMIUM")
	private CustomerSegment segment;

	@Schema(description = "Customer status", example = "ACTIVE")
	private CustomerStatus status;

	@Schema(description = "Customer phone", example = "+919876543210")
	private String phone;

	@Schema(description = "Customer creation timestamp", example = "2026-09-19T16:30:00")
	private LocalDateTime createdAt;

	@Schema(description = "Customer last update timestamp", example = "2026-09-19T16:30:00")
	private LocalDateTime updatedAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public CustomerSegment getSegment() {
		return segment;
	}

	public void setSegment(CustomerSegment segment) {
		this.segment = segment;
	}

	public CustomerStatus getStatus() {
		return status;
	}

	public void setStatus(CustomerStatus status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}