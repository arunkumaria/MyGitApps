package com.own.microservice_openfeign_emp_service.repo;

import com.own.microservice_openfeign_emp_service.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}