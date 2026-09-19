package com.own.customer.dto;

import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object used to create or update a customer")
public class CustomerRequest {

	@Schema(description = "Customer full name", example = "Arun Kumar", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "Name is required")
	@Size(max = 100, message = "Name must not exceed 100 characters")
	private String name;

	@Schema(description = "Customer email address", example = "arun@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Size(max = 150, message = "Email must not exceed 150 characters")
	private String email;

	@Schema(description = "Customer company", example = "ABC Technologies")
	@Size(max = 150, message = "Company must not exceed 150 characters")
	private String company;

	@Schema(description = "Customer segment", example = "PREMIUM", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "Segment is required")
	private CustomerSegment customerSegment;

	@Schema(description = "Customer status", example = "ACTIVE")
	private CustomerStatus customerStatus;

	@Schema(description = "Customer phone number", example = "+919876543210")
	@Pattern(regexp = "^[0-9+()\\- ]*$", message = "Invalid phone number")
	@Size(max = 30, message = "Phone must not exceed 30 characters")
	private String phone;

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

	public CustomerSegment getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(CustomerSegment customerSegment) {
		this.customerSegment = customerSegment;
	}

	public CustomerStatus getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(CustomerStatus customerStatus) {
		this.customerStatus = customerStatus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}