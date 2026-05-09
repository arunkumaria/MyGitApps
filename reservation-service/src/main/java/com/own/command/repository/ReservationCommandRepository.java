
package com.own.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.command.model.Reservation;

public interface ReservationCommandRepository extends JpaRepository<Reservation, String> {
}
