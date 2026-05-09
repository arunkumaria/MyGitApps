
package com.own.command.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.own.command.model.Reservation;
import com.own.command.repository.ReservationCommandRepository;
import com.own.common.events.ReservationCreatedEvent;
import com.own.query.repository.ReservationQueryRepository;

@Service
public class ReservationCommandService {

	private final ReservationCommandRepository repository;
	private final ReservationQueryRepository queryRepository;

	public ReservationCommandService(ReservationCommandRepository repository,
			ReservationQueryRepository queryRepository) {
		this.repository = repository;
		this.queryRepository = queryRepository;
	}

	@Transactional
	public String createReservation(Reservation reservation) {
		Reservation saved = repository.save(reservation);

		// In real systems → publish to Kafka
		new ReservationCreatedEvent(queryRepository).publish(saved);

		return saved.getId();
	}

	@Transactional
	public void cancelReservation(String id) {
		Reservation reservation = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Reservation not found"));

		reservation.setStatus(com.own.command.model.Status.CANCELLED);
		repository.save(reservation);
	}
}
