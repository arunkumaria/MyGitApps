package com.own.ems.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.own.ems.model.Employee;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, String> {

}