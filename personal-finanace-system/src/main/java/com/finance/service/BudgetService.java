package com.finance.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.finance.dto.BudgetRequest;
import com.finance.entity.Budget;
import com.finance.entity.Category;
import com.finance.entity.TransactionType;
import com.finance.entity.User;
import com.finance.repository.BudgetRepository;
import com.finance.repository.CategoryRepository;
import com.finance.repository.UserRepository;

@Service
@Transactional
public class BudgetService {

	private final BudgetRepository budgets;
	private final CategoryRepository categories;
	private final UserRepository users;

	public BudgetService(BudgetRepository budgets, CategoryRepository categories, UserRepository users) {
		this.budgets = budgets;
		this.categories = categories;
		this.users = users;
	}

	public List<Budget> list(Long userId, LocalDate month) {
		return budgets.findAllByUser_IdAndBudgetMonth(userId, month.withDayOfMonth(1));
	}

	public Budget create(Long userId, BudgetRequest request) {
		LocalDate month = request.month().withDayOfMonth(1);

		Category category = categories.findByIdAndUser_IdAndType(request.categoryId(), userId, TransactionType.EXPENSE)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"An owned expense category is required"));

		if (budgets.existsByUser_IdAndCategory_IdAndBudgetMonth(userId, category.getId(), month)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"A budget already exists for this category and month");
		}

		User user = users.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

		return budgets.save(new Budget(user, category, month, request.limitAmount()));
	}

	public Budget update(Long userId, Long id, BudgetRequest request) {
		Budget budget = budgets.findByIdAndUser_Id(id, userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Budget not found"));

		Category category = categories.findByIdAndUser_IdAndType(request.categoryId(), userId, TransactionType.EXPENSE)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid expense category"));

		LocalDate month = request.month().withDayOfMonth(1);

		boolean duplicate = budgets.existsByUser_IdAndCategory_IdAndBudgetMonth(userId, category.getId(), month);

		if (duplicate
				&& (!budget.getCategory().getId().equals(category.getId()) || !budget.getBudgetMonth().equals(month))) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate monthly budget");
		}

		// Keep the budget's category and month immutable in this version.
		if (!budget.getCategory().getId().equals(category.getId()) || !budget.getBudgetMonth().equals(month)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Delete and recreate the budget to change its category or month");
		}

		budget.update(request.limitAmount());
		return budget;
	}

	public void delete(Long userId, Long id) {
		Budget budget = budgets.findByIdAndUser_Id(id, userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Budget not found"));
		budgets.delete(budget);
	}
}
