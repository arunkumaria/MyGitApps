package com.own.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.query.model.ReservationView;

public interface ReservationQueryRepository extends JpaRepository<ReservationView, String> {
}
