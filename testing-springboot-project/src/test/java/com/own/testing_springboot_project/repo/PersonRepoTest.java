package com.own.testing_springboot_project.repo;

import com.own.testing_springboot_project.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonRepoTest {

	@Autowired
	private PersonRepo personRepo;

	@Test
	public void testExistsById() {
		Person person = new Person(null, "Test Name", "City");
		person = personRepo.save(person);
		boolean exists = personRepo.existsById(person.getPersonId());
		assertThat(exists).isTrue();
	}
}