package com.own.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.own.customer.entity.CustomerSegment2;
import com.own.customer.entity.CustomerStatus2;

public class CustomerEvent2 {
	private UUID id;
	private String name;
	private String email;
	private String company;
	private CustomerStatus2 customerStatus2;
	private CustomerSegment2 customerSegment2;
	private String eventType;
	private LocalDateTime timestamp;

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

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
