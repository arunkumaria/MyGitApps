package com.finance.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.finance.dto.CategoryRequest;
import com.finance.entity.Category;
import com.finance.entity.TransactionType;
import com.finance.entity.User;
import com.finance.repository.CategoryRepository;
import com.finance.repository.UserRepository;
import com.finance.service.DefaultCategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categories;
    private final UserRepository users;
    private final DefaultCategoryService defaultCategoryService;

    public CategoryController(
            CategoryRepository categories, UserRepository users, DefaultCategoryService defaultCategoryService) {
        this.categories = categories;
        this.users = users;
        this.defaultCategoryService = defaultCategoryService;
    }

    private User currentUser(Authentication auth) {
        return users.findByEmailIgnoreCase(auth.getName())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED));
    }

    @GetMapping
    public List<CategoryView> list(Authentication auth) {
        User user = currentUser(auth);
        defaultCategoryService.ensureFor(user);
        return categories.findAllByUser_IdOrderByNameAsc(
            user.getId())
            .stream()
            .map(c -> new CategoryView(c.getId(), c.getName(), c.getType()))
            .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryView create(
            Authentication auth,
            @Valid @RequestBody CategoryRequest request) {

        User user = currentUser(auth);
        String name = request.name().trim();

        if (name.isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Category name is required");
        }

        boolean exists = categories
            .findAllByUser_IdOrderByNameAsc(user.getId())
            .stream()
            .anyMatch(c -> c.getType() == request.type()
                && c.getName().equalsIgnoreCase(name));

        if (exists) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Category already exists");
        }

        Category saved = categories.save(
            new Category(user, name, request.type()));

        return new CategoryView(
            saved.getId(), saved.getName(), saved.getType());
    }

    public record CategoryView(
        Long id, String name, TransactionType type) {}
}
