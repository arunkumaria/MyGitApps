package com.own.common.events;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.own.command.model.Reservation;
import com.own.query.model.ReservationView;
import com.own.query.repository.ReservationQueryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReservationCreatedEvent {

	private final ReservationQueryRepository repository;

	@TransactionalEventListener
	public void publish(Reservation reservation) {
		// Simulated projection
		ReservationView view = new ReservationView();
		view.setId(reservation.getId());
		view.setGuestName(reservation.getGuestName());
		view.setReservationDate(reservation.getReservationDate());
		view.setSeats(reservation.getSeats());
		view.setStatus(reservation.getStatus().name());

		// In real system → Kafka Consumer updates read DB
		System.out.println("Projection updated for reservation " + view.getId());
		repository.save(view);
	}
}
