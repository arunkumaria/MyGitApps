package manyToMany;

import java.util.Set;

public class GeekEmployeeData {
	private int id;
	private String firstName;
	private String lastName;
	private int salary;

	// Many-to-Many: employee can have multiple skills
	private Set skillSets;

	public GeekEmployeeData() {
	}

	public GeekEmployeeData(String firstName, String lastName, int salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
	}

	// Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Set getSkillSets() {
		return skillSets;
	}

	public void setSkillSets(Set skillSets) {
		this.skillSets = skillSets;
	}
}