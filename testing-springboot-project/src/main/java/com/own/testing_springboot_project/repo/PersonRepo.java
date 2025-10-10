package com.own.testing_springboot_project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.testing_springboot_project.entities.Person;

// Interface Extends JpaRepository
public interface PersonRepo extends JpaRepository<Person, Integer> {
	// Spring data jpa will automatically provide implementation for it when using
	// existsBy{fieldName}
	boolean existsById(Integer id);
}
