package com.finance.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dto.TransactionRequest;
import com.finance.dto.TransactionResponse;
import com.finance.entity.User;
import com.finance.repository.UserRepository;
import com.finance.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService service;
	private final UserRepository users;

	public TransactionController(TransactionService service, UserRepository users) {
		this.service = service;
		this.users = users;
	}

	private Long currentUserId(Authentication auth) {
		return users.findByEmailIgnoreCase(auth.getName()).map(User::getId).orElseThrow();
	}

	@GetMapping
	public List<TransactionResponse> list(Authentication auth,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

		LocalDate today = LocalDate.now();
		return service.list(currentUserId(auth), start == null ? today.withDayOfMonth(1) : start,
				end == null ? today : end);
	}

	@GetMapping("/{id}")
	public TransactionResponse get(Authentication auth, @PathVariable Long id) {
		return service.get(currentUserId(auth), id);
	}

	@PostMapping
	public TransactionResponse create(Authentication auth, @Valid @RequestBody TransactionRequest request) {
		return service.create(currentUserId(auth), request);
	}

	@PutMapping("/{id}")
	public TransactionResponse update(Authentication auth, @PathVariable Long id,
			@Valid @RequestBody TransactionRequest request) {
		return service.update(currentUserId(auth), id, request);
	}

	@DeleteMapping("/{id}")
	@org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
	public void delete(Authentication auth, @PathVariable Long id) {
		service.delete(currentUserId(auth), id);
	}
}