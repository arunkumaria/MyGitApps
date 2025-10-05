package com.own.map.models;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "models")
public class Model {

	// Unique identifier for the model
	@Id
	private int model_id;

	// Name of the model
	private String name;

	// A model belongs to one manufacturer Foreign key referencing the manufacturer
	// table
	@ManyToOne
	@JoinColumn(name = "manufacture_id")
	private Manufactures manufacturer;

	// Constructor with all fields
	public Model(int model_id, String name, Manufactures manufacturer) {
		this.model_id = model_id;
		this.name = name;
		this.manufacturer = manufacturer;
	}

	// Default constructor
	public Model() {
	}

	// Getters and setters

	public int getModel_id() {
		return model_id;
	}

	public void setModel_id(int model_id) {
		this.model_id = model_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Manufactures getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufactures manufacturer) {
		this.manufacturer = manufacturer;
	}
}
