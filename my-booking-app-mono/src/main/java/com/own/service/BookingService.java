package com.own.service;

import org.springframework.stereotype.Service;

import com.own.dao.BookingRepository;
import com.own.dao.DriverRepository;
import com.own.dto.BookingRequest;
import com.own.model.Booking;
import com.own.model.BookingStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

	private final BookingRepository bookingRepository;
	private final DriverRepository driverRepository;

	public Booking createBookingSlot(BookingRequest bookingRequest) {

		Booking booking = new Booking(bookingRequest.getUsername(), bookingRequest.getPickupPoint(),
				bookingRequest.getDropPoint());

		driverRepository.findFirstByAvailableTrue().ifPresent(driver -> {
			booking.setDriverId(driver.getDriverId());
			booking.setStatus(BookingStatus.DRIVER_ASSIGNED);
			driver.setAvailable(false);
			driverRepository.save(driver);
		});

		return bookingRepository.save(booking);

	}

	public Booking getBookedDetails(Long id) {

		return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));

	}

}
