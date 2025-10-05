package com.own.sdj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.sdj.model.GeekUserRecord;

public interface GeekUserRepository extends JpaRepository<GeekUserRecord, Long> {
	// add custom finder methods if needed, e.g.List<GeekUserRecord>
	// findByName(String name);
}