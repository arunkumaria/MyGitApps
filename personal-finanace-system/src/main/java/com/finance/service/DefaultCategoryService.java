package com.finance.service;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.entity.Category;
import com.finance.entity.TransactionType;
import com.finance.entity.User;
import com.finance.repository.CategoryRepository;

/** Creates starter categories for a user's first visit and repairs older accounts with none. */
@Service
public class DefaultCategoryService {
    private final CategoryRepository categories;

    public DefaultCategoryService(CategoryRepository categories) {
        this.categories = categories;
    }

    @Transactional
    public void ensureFor(User user) {
        List<Category> existing = categories.findAllByUser_IdOrderByNameAsc(user.getId());
        Set<String> existingKeys = existing.stream()
            .map(c -> c.getType().name() + ":" + c.getName().trim().toLowerCase(Locale.ROOT))
            .collect(Collectors.toSet());

        List<Category> defaults = List.of(
            new Category(user, "Food & Groceries", TransactionType.EXPENSE),
            new Category(user, "Rent & Housing", TransactionType.EXPENSE),
            new Category(user, "Transport", TransactionType.EXPENSE),
            new Category(user, "Utilities", TransactionType.EXPENSE),
            new Category(user, "Healthcare", TransactionType.EXPENSE),
            new Category(user, "Entertainment", TransactionType.EXPENSE),
            new Category(user, "Shopping", TransactionType.EXPENSE),
            new Category(user, "Other Expense", TransactionType.EXPENSE),
            new Category(user, "Salary", TransactionType.INCOME),
            new Category(user, "Freelance", TransactionType.INCOME),
            new Category(user, "Business", TransactionType.INCOME),
            new Category(user, "Investment", TransactionType.INCOME),
            new Category(user, "Other Income", TransactionType.INCOME)
        );

        List<Category> missing = defaults.stream()
            .filter(c -> !existingKeys.contains(c.getType().name() + ":" + c.getName().toLowerCase(Locale.ROOT)))
            .toList();
        if (!missing.isEmpty()) {
            categories.saveAll(missing);
        }
    }
}
