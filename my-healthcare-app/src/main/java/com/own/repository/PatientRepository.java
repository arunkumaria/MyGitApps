package com.own.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.own.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	List<Patient> findByLastNameContainingIgnoreCase(String lastName);
}
