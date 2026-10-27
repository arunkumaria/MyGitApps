package com.finance.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.finance.entity.Transaction;
import com.finance.entity.TransactionType;
import com.finance.repository.TransactionRepository;
import com.finance.repository.UserRepository;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public DashboardController(
            TransactionRepository transactionRepository,
            UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public Map<String, Object> getDashboard(
            @RequestParam(required = false) String month,
            Authentication authentication) {

        if (authentication == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Please sign in.");
        }

        YearMonth selectedMonth;

        try {
            selectedMonth = (month == null || month.isBlank())
                    ? YearMonth.now()
                    : YearMonth.parse(month);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Month must use YYYY-MM format.");
        }

        Long userId = userRepository
                .findByEmailIgnoreCase(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found."))
                .getId();

        LocalDate start = selectedMonth.atDay(1);
        LocalDate end = selectedMonth.atEndOfMonth();

        BigDecimal income = transactionRepository
                .sumByUserAndTypeAndDate(
                        userId, TransactionType.INCOME, start, end);

        BigDecimal expenses = transactionRepository
                .sumByUserAndTypeAndDate(
                        userId, TransactionType.EXPENSE, start, end);

        BigDecimal balance = income.subtract(expenses);

        List<Transaction> transactions =
                transactionRepository
                    .findAllByUser_IdAndTransactionDateBetweenOrderByTransactionDateDesc(
                            userId, start, end);

        Map<String, BigDecimal> spendingByCategory = new LinkedHashMap<>();
        transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .forEach(t -> spendingByCategory.merge(t.getCategory().getName(), t.getAmount(), BigDecimal::add));
        List<Map<String, Object>> categoryBreakdown = new ArrayList<>();
        spendingByCategory.forEach((category, amount) -> categoryBreakdown.add(Map.of("category", category, "amount", amount)));

        List<Map<String, Object>> recentTransactions =
                transactions.stream()
                    .limit(5)
                    .map(t -> Map.<String, Object>of(
                        "id", t.getId(),
                        "type", t.getType().name(),
                        "amount", t.getAmount(),
                        "date", t.getTransactionDate().toString(),
                        "category", t.getCategory().getName(),
                        "notes", t.getNotes() == null ? "" : t.getNotes()
                    ))
                    .toList();

        return Map.of(
                "month", selectedMonth.toString(),
                "totalIncome", income,
                "totalExpenses", expenses,
                "balance", balance,
                "categoryBreakdown", categoryBreakdown,
                "recentTransactions", recentTransactions
        );
    }
}