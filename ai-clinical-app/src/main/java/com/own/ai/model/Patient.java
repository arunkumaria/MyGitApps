package com.own.ai.model;

import java.time.LocalDateTime;

public record Patient(String id, String mrn, String firstName, String lastName, String dateOfBirth, String gender,
		LocalDateTime createdAt) {
}
