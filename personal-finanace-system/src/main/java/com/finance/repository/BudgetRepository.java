package com.finance.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

	@EntityGraph(attributePaths = "category")
	List<Budget> findAllByUser_IdAndBudgetMonth(Long userId, LocalDate month);

	@EntityGraph(attributePaths = "category")
	Optional<Budget> findByIdAndUser_Id(Long id, Long userId);

	boolean existsByUser_IdAndCategory_IdAndBudgetMonth(Long userId, Long categoryId, LocalDate month);
}
