package com.own;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.own.entity.Product;
import com.own.repository.ProductRepository;

@SpringBootApplication
public class EBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ProductRepository repo) {
        return args -> {
            repo.save(new Product(null, "Laptop", 50000, "https://via.placeholder.com/150"));
            repo.save(new Product(null, "Phone", 20000, "https://via.placeholder.com/150"));
        };
    }
}