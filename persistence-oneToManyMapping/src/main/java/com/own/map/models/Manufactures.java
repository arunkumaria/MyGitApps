package com.own.map.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "manufactures")
public class Manufactures {

	// Unique identifier for the manufacturer
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// Name of the manufacturer
	private String manufactures_name;

	// A manufacturer can have many models
	@OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
	private List<Model> models;

	// Constructor with both id and name
	public Manufactures(String manufactures_name) {
		this.manufactures_name = manufactures_name;
	}

	// Default constructor
	public Manufactures() {
	}

	// Getters and setters for id and name

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getManufactures_name() {
		return manufactures_name;
	}

	public void setManufactures_name(String manufactures_name) {
		this.manufactures_name = manufactures_name;
	}

	// Getter for models
	public List<Model> getModels() {
		return models;
	}

	// Setter for models
	public void setModels(List<Model> models) {
		this.models = models;
	}
}
