package com.finance.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.finance.dto.TransactionRequest;
import com.finance.dto.TransactionResponse;
import com.finance.entity.Category;
import com.finance.entity.Transaction;
import com.finance.entity.TransactionType;
import com.finance.entity.User;
import com.finance.repository.CategoryRepository;
import com.finance.repository.TransactionRepository;
import com.finance.repository.UserRepository;

@Service
@Transactional
public class TransactionService {

	private final TransactionRepository transactions;
	private final CategoryRepository categories;
	private final UserRepository users;

	public TransactionService(TransactionRepository transactions, CategoryRepository categories, UserRepository users) {
		this.transactions = transactions;
		this.categories = categories;
		this.users = users;
	}

	public List<TransactionResponse> list(Long userId, LocalDate start, LocalDate end) {

		if (start.isAfter(end)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must not be after end date");
		}

		return transactions.findAllByUser_IdAndTransactionDateBetweenOrderByTransactionDateDesc(userId, start, end)
				.stream().map(TransactionResponse::from).toList();
	}

	public TransactionResponse get(Long userId, Long id) {
		return TransactionResponse.from(findOwned(userId, id));
	}

	public TransactionResponse create(Long userId, TransactionRequest request) {

		User user = users.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

		Category category = findCategory(request.categoryId(), userId, request.type());

		Transaction tx = new Transaction(user, category, request.type(), request.amount(), request.transactionDate(),
				request.notes());

		return TransactionResponse.from(transactions.save(tx));
	}

	public TransactionResponse update(Long userId, Long id, TransactionRequest request) {

		Transaction tx = findOwned(userId, id);

		Category category = findCategory(request.categoryId(), userId, request.type());

		tx.update(category, request.type(), request.amount(), request.transactionDate(), request.notes());

		return TransactionResponse.from(tx);
	}

	public void delete(Long userId, Long id) {
		transactions.delete(findOwned(userId, id));
	}

	private Transaction findOwned(Long userId, Long id) {
		return transactions.findByIdAndUser_Id(id, userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
	}

	private Category findCategory(Long categoryId, Long userId, TransactionType type) {

		return categories.findByIdAndUser_IdAndType(categoryId, userId, type)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Category not found, not owned by user, or type mismatch"));
	}
}