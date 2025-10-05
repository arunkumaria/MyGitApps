package com.own.springboot_10best.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
	private final ProductRepository repo;
	private final ProductService service;

	public ProductController(ProductRepository repo, ProductService service) {
		this.repo = repo;
		this.service = service;
	}

	@PostMapping
	public Product create(@RequestBody Product p) {
		return repo.save(p);
	}

	@GetMapping("/page")
	public Page<Product> page(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String sort) {
		String[] s = sort.split(",");
		Sort.Direction dir = s.length > 1 && s[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		return repo.findAll(PageRequest.of(page, size, Sort.by(dir, s[0])));
	}

	@GetMapping("/{id}")
	public Product get(@PathVariable Long id) {
		return service.getProduct(id);
	}
}
