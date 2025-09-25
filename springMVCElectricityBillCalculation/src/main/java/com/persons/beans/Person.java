package com.persons.beans;

public class Person {

	private int id;
	private String name;
	private String serviceNumber;
	private int consumedUnits;
	private String gender;

	public int getConsumedUnits() {
		return consumedUnits;
	}

	public void setConsumedUnits(int consumedUnits) {
		this.consumedUnits = consumedUnits;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

}