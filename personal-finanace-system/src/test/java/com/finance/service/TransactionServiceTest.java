package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.finance.dto.TransactionRequest;
import com.finance.entity.TransactionType;
import com.finance.repository.CategoryRepository;
import com.finance.repository.TransactionRepository;
import com.finance.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

	@Mock
	TransactionRepository transactions;
	@Mock
	CategoryRepository categories;
	@Mock
	UserRepository users;

	@InjectMocks
	TransactionService service;

	@Test
	void rejectsCategoryNotOwnedByUser() {
		when(users.findById(1L)).thenReturn(Optional.empty());

		TransactionRequest request = new TransactionRequest(TransactionType.EXPENSE, new BigDecimal("100.00"), 99L,
				LocalDate.of(2026, 10, 20), "Test");

		// The service requires an existing user before checking
		// the category. No transaction should be persisted.
		assertThrows(ResponseStatusException.class, () -> service.create(1L, request));

		verify(transactions, never()).save(any());
	}
}
