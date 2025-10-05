package com.own.map.OneToManyMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.own.map.models.Manufactures;
import com.own.map.models.Model;
import com.own.map.repo.ManufacturesRepo;
import com.own.map.repo.ModelRepo;

/**
 * Spring Boot application for mapping relationships between Manufactures and
 * Models.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.own.map.repo")
@EntityScan(basePackages = "com.own.map") 
public class MappingApplication implements CommandLineRunner {

	// Injecting Manufactures and Model repositories using autowiring
	@Autowired
	private ManufacturesRepo manufacturesRepo;
	@Autowired
	private ModelRepo modelRepo;

	public static void main(String[] args) {
		SpringApplication.run(MappingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Creating a new Manufactures object with ID 1 and name "Honda"
		Manufactures data = new Manufactures("Honda");

		// Saving the Manufactures record
		manufacturesRepo.save(data);

		// Creating two new Model objects associated with the "Honda" manufacturer
		Model model1 = new Model(1, "AYZ", data);
		Model model2 = new Model(2, "ZET", data);

		// Saving the Model records
		modelRepo.save(model1);
		modelRepo.save(model2);
	}
}
