package com.finance.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finance.entity.Transaction;
import com.finance.entity.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	// Find a transaction by ID and owner
	Optional<Transaction> findByIdAndUser_Id(Long id, Long userId);

	// Find a user's transactions within a date range
	List<Transaction> findAllByUser_IdAndTransactionDateBetweenOrderByTransactionDateDesc(Long userId, LocalDate start,
			LocalDate end);

	// Calculate total income or expenses for a user within a date range
	@Query("""
			SELECT COALESCE(SUM(t.amount), 0)
			FROM Transaction t
			WHERE t.user.id = :userId
			  AND t.type = :type
			  AND t.transactionDate BETWEEN :start AND :end
			""")
	BigDecimal sumByUserAndTypeAndDate(@Param("userId") Long userId, @Param("type") TransactionType type,
			@Param("start") LocalDate start, @Param("end") LocalDate end);

	// Calculate total spending for a user and category within a date range
	@Query("""
			SELECT COALESCE(SUM(t.amount), 0)
			FROM Transaction t
			WHERE t.user.id = :userId
			  AND t.type = :type
			  AND t.category.id = :categoryId
			  AND t.transactionDate BETWEEN :start AND :end
			""")
	BigDecimal sumByUserAndCategoryAndTypeAndDate(@Param("userId") Long userId, @Param("categoryId") Long categoryId,
			@Param("type") TransactionType type, @Param("start") LocalDate start, @Param("end") LocalDate end);
}
