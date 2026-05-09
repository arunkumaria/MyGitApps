
package com.own.query.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reservation_view")
public class ReservationView {

	@Id
	private String id;
	private String guestName;
	private LocalDate reservationDate;
	private int seats;
	private String status;

	// getters & setters
}
