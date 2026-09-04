package com.own.customer.dto;

import com.own.customer.entity.CustomerSegment;
import com.own.customer.entity.CustomerStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerRequest {

	@NotBlank(message = "name should not be blank")
	@Size(max = 100, message = "name should not exceed 100 characters")
	private String name;

	@NotBlank(message = "email should not be blank")
	@Size(max = 150)
	@Email(message = "email is not valid")
	private String email;

	@Size(max = 30)
	private String phone;

	@Size(max = 30)
	private String company;

	private CustomerStatus customerStatus;
	private CustomerSegment customerSegment;

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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public CustomerStatus getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(CustomerStatus customerStatus) {
		this.customerStatus = customerStatus;
	}

	public CustomerSegment getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(CustomerSegment customerSegment) {
		this.customerSegment = customerSegment;
	}

}
