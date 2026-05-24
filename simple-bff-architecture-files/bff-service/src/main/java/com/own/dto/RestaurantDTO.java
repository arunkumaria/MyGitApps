package com.own.dto;

public class RestaurantDTO {

	private Long id;
	private String name;
	private String location;
	private Double rating;

	public RestaurantDTO() {
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public Double getRating() {
		return rating;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
}
