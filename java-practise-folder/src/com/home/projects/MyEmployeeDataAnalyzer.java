package com.home.projects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//1. employee
class Emp {
	private int id;
	private String name;
	private double salary;

	public Emp(int id, String name, double salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Optional<Double> getSalary() {
		return Optional.ofNullable(salary);
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String toString() {
		return "id: " + id + " " + "name: " + name + " " + "salary: " + salary;
	}

}

//2. employee manager
class EmpManager {

	Map<Integer, Emp> empData = new HashMap();

	public void addEmployee(Emp emp) {
		empData.put(emp.getId(), emp);
	}

	public Optional<Emp> getEmployeeById(int id) {
		return Optional.ofNullable(empData.get(id));

	}

	public List<Emp> getAllEmployees() {
		return new ArrayList<>(empData.values());
	}

	public List<Emp> getEmployeesBySorted() {
		return empData.values().stream().sorted(Comparator.comparingDouble(e -> e.getSalary().orElse(0.0)))
				.collect(Collectors.toList());
	}

	public List<Emp> getEmployeesByMinSalary(double min) {

		return empData.values().stream().filter(emp -> emp.getSalary().orElse(0.0) >= min).collect(Collectors.toList());

	}

}

//3. employee processor
class EmpProcessor extends Thread {

	private int employeeId;
	private EmpManager employeeManager;

	EmpProcessor(int id, EmpManager employeeManager) {
		this.employeeId = id;
		this.employeeManager = employeeManager;
	}

	public void run() {
		try {
			Optional<Emp> emp = employeeManager.getEmployeeById(employeeId);
			emp.ifPresent(e -> System.out.println("processing the employee id " + e.getId()));
			emp.orElseThrow(() -> new EmpNotFoundException("emp id " + employeeId + " is missing"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

//4. exception
class EmpNotFoundException extends RuntimeException {
	public EmpNotFoundException(String msg) {
		super(msg);
	}

}

//5. employee data analyzer
public class MyEmployeeDataAnalyzer {

	public static void main(String[] args) {
		EmpManager employeeManager = new EmpManager();

		employeeManager.addEmployee(new Emp(1, "Arun", 500000.0));
		employeeManager.addEmployee(new Emp(2, "Tharun", 100000.0));
		employeeManager.addEmployee(new Emp(3, "Varun", 300000.0));

		EmpProcessor empProcessor1 = new EmpProcessor(1, employeeManager);
		EmpProcessor empProcessor2 = new EmpProcessor(2, employeeManager);
		EmpProcessor empProcessor3 = new EmpProcessor(4, employeeManager);
		EmpProcessor empProcessor4 = new EmpProcessor(5, employeeManager);
		EmpProcessor empProcessor5 = new EmpProcessor(6, employeeManager);

		empProcessor1.start();
		empProcessor2.start();
		empProcessor3.start();
		empProcessor4.start();
		empProcessor5.start();

		try {
			empProcessor1.join();
			empProcessor2.join();
			empProcessor3.join();
		} catch (Exception e) {
			e.getMessage();
		}

		System.out.println("min salary");
		List<Emp> empM = employeeManager.getEmployeesByMinSalary(500000);
		empM.forEach(System.out::println);

		System.out.println("sorted salary");
		List<Emp> empS = employeeManager.getEmployeesBySorted();
		empS.forEach(System.out::println);
	}

}
