package com.own.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.own.customer.entity.CustomerSegment2;
import com.own.customer.entity.CustomerStatus2;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "customer response")
public class CustomerResponse2 {
	@Schema(description = "customer id", example = "12345")
	private UUID id;
	@Schema(description = "customer name", example = "Arun")
	private String name;
	@Schema(description = "customer phone", example = "9876543210")
	private String phone;
	@Schema(description = "customer email", example = "arun@gmail.com")
	private String email;
	@Schema(description = "customer status", example = "ACTIVE")
	private CustomerStatus2 customerStatus2;
	@Schema(description = "customer segment", example = "STANDARD")
	private CustomerSegment2 customerSegment2;
	@Schema(description = "customer creation timestamp", example = "2026-11-19T05:31:30")
	private LocalDateTime createdAt;
	@Schema(description = "customer updation timestamp", example = "2026-11-19T05:31:30")
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CustomerStatus2 getCustomerStatus2() {
		return customerStatus2;
	}

	public void setCustomerStatus2(CustomerStatus2 customerStatus2) {
		this.customerStatus2 = customerStatus2;
	}

	public CustomerSegment2 getCustomerSegment2() {
		return customerSegment2;
	}

	public void setCustomerSegment2(CustomerSegment2 customerSegment2) {
		this.customerSegment2 = customerSegment2;
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
