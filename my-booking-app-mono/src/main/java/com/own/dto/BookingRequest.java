package com.own.dto;

import lombok.Data;

@Data
public class BookingRequest {

	private String username;
	private String pickupPoint;
	private String dropPoint;

}
