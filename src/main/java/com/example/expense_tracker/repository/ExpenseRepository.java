package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query(
            "SELECT e FROM Expense e WHERE " +
            "(:category IS NULL OR e.category = :category) AND " +
            "(:startDate IS NULL or e.date >= :startDate) AND " +
            "(:endDate IS NULL or e.date <= :endDate)"
    )
    List<Expense> filterExpenses(String category, LocalDate startDate, LocalDate endDate);
}
