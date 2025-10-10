package com.own.testing_springboot_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.testing_springboot_project.entities.Person;
import com.own.testing_springboot_project.services.PersonService;

import java.util.List;

@RestController
public class PersonController {
	// Constructor Based Injection No need of using @Autowired
	private final PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping("/persons")
	public ResponseEntity<List<Person>> getAllPersons() {
		return ResponseEntity.ok(personService.getAllPerson());
		
	}
}