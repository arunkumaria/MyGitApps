package com.own.query.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.query.model.ReservationView;
import com.own.query.service.ReservationQueryService;

@RestController
@RequestMapping("/api/queries/reservations")
public class ReservationQueryController {

	private final ReservationQueryService service;

	public ReservationQueryController(ReservationQueryService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ReservationView getById(@PathVariable String id) {
		return service.getById(id);
	}

	@GetMapping
	public List<ReservationView> getAll() {
		return service.getAll();
	}
}
