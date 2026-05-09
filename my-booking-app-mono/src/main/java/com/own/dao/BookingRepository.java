package com.own.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
