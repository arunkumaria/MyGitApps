package com.own.springboot_10best.product;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	private final ProductRepository repo;

	public ProductService(ProductRepository repo) {
		this.repo = repo;
	}

	@Cacheable("products")
	public Product getProduct(Long id) {
		return repo.findById(id).orElseThrow();
	}
}
