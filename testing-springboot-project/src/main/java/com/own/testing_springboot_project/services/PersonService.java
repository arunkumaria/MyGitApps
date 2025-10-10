package com.own.testing_springboot_project.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.own.testing_springboot_project.entities.Person;
import com.own.testing_springboot_project.repo.PersonRepo;

@Service
public class PersonService {
	// No need to use @Autowired when using Constructor Injection Dependencies are
	// final

	private final PersonRepo repo;

	public PersonService(PersonRepo repo) {
		// this keyword refers to current instance
		this.repo = repo;
	}

	public List<Person> getAllPerson() {
		return repo.findAll();
	}
}