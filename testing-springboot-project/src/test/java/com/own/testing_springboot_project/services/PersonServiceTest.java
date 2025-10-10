package com.own.testing_springboot_project.services;


//USing BDD Mockito 
import static org.mockito.BDDMockito.verify;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.own.testing_springboot_project.entities.Person;
import com.own.testing_springboot_project.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
  @Mock
private PersonRepo personRepo;
//When using Mockito Use @InjectMocks to inject Mocked beans to following class
@InjectMocks
private PersonService personService;
@Test
void getAllPerson() {
	Person person1 = new Person(1, "Rahul Sharma", "Delhi");
  Person person2 = new Person(2, "Anita Singh", "Mumbai");
  given(personRepo.findAll()).willReturn(List.of(person1, person2));
  List<Person> personList = personService.getAllPerson();
  assertThat(personList).isNotNull();
  assertThat(personList.size()).isEqualTo(2);
  verify(personRepo).findAll(); // Optional but recommended
}
}