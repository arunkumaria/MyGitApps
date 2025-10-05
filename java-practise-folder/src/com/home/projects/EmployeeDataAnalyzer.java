package com.home.projects;

import java.util.Optional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.List;

class Employee {
	private int id;
	private String name;
	private String department;
	private Double salary;

	public Employee(int id, String name, String department, Double salary) {
		this.id = id;
		this.name = name;
		this.department = department;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDepartment() {
		return department;
	}

	public Optional<Double> getSalary() {
		return Optional.ofNullable(salary);
	}

	@Override
	public String toString() {
		return "Employee{id=" + id + ", name='" + name + "', department='" + department + "', salary=" + salary + '}';
	}
}

class EmployeeNotFoundException extends Exception {
	public EmployeeNotFoundException(String message) {
		super(message);
	}
}

class EmployeeManager {
	private Map<Integer, Employee> employeeData;

	public EmployeeManager() {
		this.employeeData = new HashMap<>();
	}

	public void addEmployee(Employee employee) {
		employeeData.put(employee.getId(), employee);
	}

	public Optional<Employee> getEmployeeById(int id) {
		return Optional.ofNullable(employeeData.get(id));
	}

	public List<Employee> getAllEmployees() {
		return new ArrayList<>(employeeData.values());
	}

	public List<Employee> filterEmployeesBySalary(Double minSalary) {
		return employeeData.values().stream().filter(employee -> employee.getSalary().orElse(0.0) >= minSalary)
				.collect(Collectors.toList());
	}

	public List<Employee> sortEmployeesBySalary() {
		return employeeData.values().stream().sorted(Comparator.comparingDouble(e -> e.getSalary().orElse(0.0)))
				.collect(Collectors.toList());
	}
}

class EmployeeProcessor extends Thread {
	private EmployeeManager employeeManager;
	private int employeeId;

	public EmployeeProcessor(EmployeeManager employeeManager, int employeeId) {
		this.employeeManager = employeeManager;
		this.employeeId = employeeId;
	}

	@Override
	public void run() {
		try {
			Optional<Employee> employee = employeeManager.getEmployeeById(employeeId);
			employee.ifPresent(emp -> System.out.println("Processing employee: " + emp));
			employee.orElseThrow(()->new EmployeeNotFoundException("missing in the employee id "+employeeId ));

//			employee.ifPresentOrElse(emp -> System.out.println("Processing employee: " + emp),
//					() -> System.out.println("Employee with ID " + employeeId + " not found."));
		} catch (Exception e) {
			System.out.println("Error processing employee data: " + e.getMessage());
		}
	}
}

public class EmployeeDataAnalyzer {
	public static void main(String[] args) {
		EmployeeManager employeeManager = new EmployeeManager();

		// Adding employees to the system
		employeeManager.addEmployee(new Employee(1, "Geek1", "Engineering", 75000.0));
		employeeManager.addEmployee(new Employee(2, "Geek2", "Marketing", 68000.0));
		employeeManager.addEmployee(new Employee(3, "Geek3", "Engineering", 80000.0));
		employeeManager.addEmployee(new Employee(4, "Geek4", "HR", 55000.0));

		// Simulate multithreading: Processing employee by ID
		Thread processor1 = new EmployeeProcessor(employeeManager, 1);
		Thread processor2 = new EmployeeProcessor(employeeManager, 2);
		Thread processor3 = new EmployeeProcessor(employeeManager, 5); // Non-existing employee

		processor1.start();
		processor2.start();
		processor3.start();

		// Wait for threads to complete
		try {
			processor1.join();
			processor2.join();
			processor3.join();
		} catch (InterruptedException e) {
			System.out.println("Error waiting for thread completion: " + e.getMessage());
		}

		// Filtering employees based on salary
		System.out.println("\nEmployees with salary >= 70000:");
		List<Employee> highEarners = employeeManager.filterEmployeesBySalary(70000.0);
		highEarners.forEach(System.out::println);

		// Sorting employees by salary
		System.out.println("\nEmployees sorted by salary:");
		List<Employee> sortedEmployees = employeeManager.sortEmployeesBySalary();
		sortedEmployees.forEach(System.out::println);
	}
}
