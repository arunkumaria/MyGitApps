package com.own.dto;


public class FoodDTO {

	private Long id;
	private String name;
	private Double price;
	private Long restaurantId;

	public FoodDTO() {
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
}