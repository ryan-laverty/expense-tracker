package com.example.expense_tracker.service;

import com.example.expense_tracker.model.Budget;
import com.example.expense_tracker.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    /**
     * Create or update a budget entry.
     * If a budget for the same category and month exists, it will be overwritten.
     *
     * @param budget The budget to save.
     * @return The saved budget.
     */
    public Budget saveBudget(Budget budget) {
        Optional<Budget> existing = budgetRepository.findByCategoryAndMonth(budget.getCategory(), budget.getMonth());
        if (existing.isPresent()) {
            Budget existingBudget = existing.get();
            existingBudget.setAmount(budget.getAmount());
            return budgetRepository.save(existingBudget);
        } else {
            return budgetRepository.save(budget);
        }
    }

    /**
     * Retrieve all budgets.
     *
     * @return List of all budgets.
     */
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    /**
     * Find budget by category and month.
     *
     * @param category Category name.
     * @param month    YearMonth object representing the month.
     * @return Optional of Budget if found.
     */
    public Optional<Budget> findByCategoryAndMonth(String category, YearMonth month) {
        return budgetRepository.findByCategoryAndMonth(category, month);
    }
}
