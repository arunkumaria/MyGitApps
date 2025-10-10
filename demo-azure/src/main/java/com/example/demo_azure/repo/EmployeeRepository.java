package com.example.demo_azure.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_azure.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
