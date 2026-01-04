package com.own.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.own.model.Patient;
import com.own.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;

	public Patient createPatient(Patient patient) {
		return patientRepository.save(patient);
	}

	public Patient getPatient(Long id) {
		return patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
	}

	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	public Patient updatePatient(Long id, Patient patientDetails) {
		Patient patient = getPatient(id);
		patient.setFirstName(patientDetails.getFirstName());
		patient.setLastName(patientDetails.getLastName());
		patient.setDateOfBirth(patientDetails.getDateOfBirth());
		patient.setEmail(patientDetails.getEmail());
		patient.setPhone(patientDetails.getPhone());
		patient.setMedicalHistory(patientDetails.getMedicalHistory());
		patient.setAllergies(patientDetails.getAllergies());
		return patientRepository.save(patient);
	}

	public void deletePatient(Long id) {
		patientRepository.deleteById(id);
	}
}
