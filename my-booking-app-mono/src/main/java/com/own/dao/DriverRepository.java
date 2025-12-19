package com.own.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.model.Driving;

public interface DriverRepository extends JpaRepository<Driving, Long> {

	Optional<Driving> findFirstByAvailableTrue();
}
