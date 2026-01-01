
package com.own.command.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {

	@Id
	private String id;

	private String guestName;
	private LocalDate reservationDate;
	private int seats;

	@Enumerated(EnumType.STRING)
	private Status status;

	public Reservation() {
		this.id = UUID.randomUUID().toString();
		this.status = Status.CREATED;
	}

	// getters & setters
}
