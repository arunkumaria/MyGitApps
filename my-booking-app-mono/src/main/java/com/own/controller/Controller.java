package com.own.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.BookingRequest;
import com.own.model.Booking;
import com.own.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/booking-app/v1")
@RequiredArgsConstructor
public class Controller {

	private final BookingService bookingService;

	@PostMapping
	public Booking createBooking(@RequestBody BookingRequest bookingRequest) {
		return bookingService.createBookingSlot(bookingRequest);

	}

	@GetMapping("/{id}")
	public Booking getBooking(@PathVariable Long id) {
		return bookingService.getBookedDetails(id);
	}

}
