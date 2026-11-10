package com.own.customer.dto;

import com.own.customer.entity.CustomerSegment2;
import com.own.customer.entity.CustomerStatus2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "request for customer creation and updation")
public class CustomerRequest2 {

	@Schema(description = "customer full name", example = "Arun Kumar I A", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "name should not be empty")
	@Size(max = 100, message = "name should not exceed 100 characters")
	private String name;

	@Schema(description = "customer email", example = "arun@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "email should not be empty")
	@Email(message = "enter email in correct format")
	@Size(max = 150, message = "email should not exceed 150 chracters")
	private String email;

	@Schema(description = "customer phone number", example = "9876543210")
	@Pattern(regexp = "^[0-9+()\\-]*$", message = "invalid phone number")
	@Size(max = 30, message = "phone number should not exceed 30 characters")
	private String phone;

	@Schema(description = "customer segment", example = "STANDARD", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "segment should not be null")
	private CustomerSegment2 customerSegment2;

	@Schema(description = "customer status", example = "ACTIVE")
	private CustomerStatus2 customerStatus2;

	@Schema(description = "customer company", example = "ABC technologies")
	@Size(max = 150, message = "company should not exceed 150 characters")
	private String company;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public CustomerSegment2 getCustomerSegment2() {
		return customerSegment2;
	}

	public void setCustomerSegment2(CustomerSegment2 customerSegment2) {
		this.customerSegment2 = customerSegment2;
	}

	public CustomerStatus2 getCustomerStatus2() {
		return customerStatus2;
	}

	public void setCustomerStatus2(CustomerStatus2 customerStatus2) {
		this.customerStatus2 = customerStatus2;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
