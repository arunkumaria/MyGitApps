
package com.own.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.own.query.model.ReservationView;
import com.own.query.repository.ReservationQueryRepository;

@Service
public class ReservationQueryService {

	private final ReservationQueryRepository repository;

	public ReservationQueryService(ReservationQueryRepository repository) {
		this.repository = repository;
	}

	public ReservationView getById(String id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
	}

	public List<ReservationView> getAll() {
		return repository.findAll();
	}
}
