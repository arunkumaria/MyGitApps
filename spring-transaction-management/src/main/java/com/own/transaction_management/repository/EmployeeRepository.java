package com.own.transaction_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.own.transaction_management.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
