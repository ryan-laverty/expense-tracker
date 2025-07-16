package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByCategoryAndMonth(String category, YearMonth month);
}
