package com.example.expense_tracker.service;

import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository repo;

    /**
     * Creates and saves a new Expense.
     *
     * @param expense Expense object to save
     * @return Saved Expense
     */
    public Expense createExpense(Expense expense) {
        return repo.save(expense);
    }

    /**
     * Retrieves all expenses.
     *
     * @return List of all expenses
     */
    public List<Expense> getAllExpenses() {
        return repo.findAll();
    }

    /**
     * Filters expenses by optional category and date range.
     *
     * @param category  Optional category filter
     * @param startDate Optional start date filter (inclusive)
     * @param endDate   Optional end date filter (inclusive)
     * @return List of filtered expenses
     */
    public List<Expense> filterExpenses(String category, LocalDate startDate, LocalDate endDate) {
        return repo.filterExpenses(category, startDate, endDate);
    }
}
