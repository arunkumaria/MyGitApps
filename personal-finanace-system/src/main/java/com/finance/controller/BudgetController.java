package com.finance.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dto.BudgetRequest;
import com.finance.entity.Budget;
import com.finance.entity.User;
import com.finance.repository.UserRepository;
import com.finance.service.BudgetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

	private final BudgetService service;
	private final UserRepository users;

	public BudgetController(BudgetService service, UserRepository users) {
		this.service = service;
		this.users = users;
	}

	private Long userId(Authentication auth) {
		return users.findByEmailIgnoreCase(auth.getName()).map(User::getId).orElseThrow();
	}

	@GetMapping
	public List<BudgetView> list(Authentication auth,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {

		return service.list(userId(auth), month).stream().map(BudgetView::from).toList();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BudgetView create(Authentication auth, @Valid @RequestBody BudgetRequest request) {
		return BudgetView.from(service.create(userId(auth), request));
	}

	@PutMapping("/{id}")
	public BudgetView update(Authentication auth, @PathVariable Long id, @Valid @RequestBody BudgetRequest request) {
		return BudgetView.from(service.update(userId(auth), id, request));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(Authentication auth, @PathVariable Long id) {
		service.delete(userId(auth), id);
	}

	public record BudgetView(Long id, Long categoryId, String category, LocalDate month,
			java.math.BigDecimal limitAmount) {

		static BudgetView from(Budget b) {
			return new BudgetView(b.getId(), b.getCategory().getId(), b.getCategory().getName(), b.getBudgetMonth(),
					b.getLimitAmount());
		}
	}
}