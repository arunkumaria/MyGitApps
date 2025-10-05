package com.own.spring_boot_jwt_token;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class GeekEmployee {

	@NotBlank(message = "Employee name cannot be blank")
	private String geekEmployeeName;

	@Email(message = "Email should be valid")
	private String geekEmailId;

	@Min(value = 10000, message = "Salary must be at least 10,000")
	private double salary;

	@NotEmpty(message = "Qualifications cannot be empty")
	private List<String> qualifications;

	// Getters and Setters
	public String getGeekEmployeeName() {
		return geekEmployeeName;
	}

	public void setGeekEmployeeName(String geekEmployeeName) {
		this.geekEmployeeName = geekEmployeeName;
	}

	public String getGeekEmailId() {
		return geekEmailId;
	}

	public void setGeekEmailId(String geekEmailId) {
		this.geekEmailId = geekEmailId;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public List<String> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<String> qualifications) {
		this.qualifications = qualifications;
	}
}
