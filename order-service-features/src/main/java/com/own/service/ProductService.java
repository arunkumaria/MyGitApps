package com.own.service;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.own.entity.Product;
import com.own.exceptions.InsufficientStockException;
import com.own.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

    private final Map<String, Product> db = new HashMap<>();

    public Product add(Product p) {
        p.setId(UUID.randomUUID().toString());
        db.put(p.getId(), p);
        return p;
    }

    public Product get(String id) {
        Product p = db.get(id);
        if (p == null) throw new ResourceNotFoundException("Product not found");
        return p;
    }

    public void reduceStock(String id, int qty) {
        Product p = get(id);
        if (p.getStock() < qty)
            throw new InsufficientStockException("Insufficient stock");
        p.setStock(p.getStock() - qty);
    }
}
