package com.own.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String username;
	private String pickupPoint;
	private String dropPoint;

	@Enumerated(EnumType.STRING)
	BookingStatus status;

	private Long driverId;

	public Booking(String username, String pickupPoint, String dropPoint) {
		this.username = username;
		this.pickupPoint = pickupPoint;
		this.dropPoint = dropPoint;
		this.status = BookingStatus.CREATED;
	}

}
