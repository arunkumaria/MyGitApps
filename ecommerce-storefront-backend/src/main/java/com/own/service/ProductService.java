package com.own.service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.own.entity.Product;
import com.own.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository repo;

	public ProductService(ProductRepository repo) {
		this.repo = repo;
	}

	public List<Product> getAll() {
		return repo.findAll();
	}

	public Product save(Product product) {
		return repo.save(product);
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
}