package com.own.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.Patient;
import com.own.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService patientService;

	@PostMapping
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
		return ResponseEntity.ok(patientService.createPatient(patient));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
		return ResponseEntity.ok(patientService.getPatient(id));
	}

	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatients() {
		return ResponseEntity.ok(patientService.getAllPatients());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
		return ResponseEntity.ok(patientService.updatePatient(id, patient));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
		patientService.deletePatient(id);
		return ResponseEntity.noContent().build();
	}
}