package com.example.expense_tracker.controller;

import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    /**
     * POST /api/expenses
     * Creates a new expense record.
     *
     * @param expense The Expense object from the request body; must be valid.
     * @return The saved Expense object including generated ID.
     */
    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        Expense savedExpense = expenseService.createExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }

    /**
     * GET /api/expenses
     * Retrieves all expenses from the database.
     *
     * @return List of all Expense records.
     */
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    /**
     * GET /api/expenses/filter
     * Filters expenses by optional month (yyyy-MM) and/or category.
     *
     * @param month    Optional query param in 'yyyy-MM' format, e.g. '2025-07'
     * @param category Optional category filter, e.g. 'Travel'
     * @return List of filtered expenses
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Expense>> filterExpenses(
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String category
    ) {
        LocalDate startDate = null;
        LocalDate endDate = null;
        if (month != null) {
            YearMonth yearMonth = YearMonth.parse(month);
            startDate = yearMonth.atDay(1);
            endDate = yearMonth.atEndOfMonth();
        }

        List<Expense> filtered = expenseService.filterExpenses(category, startDate, endDate);
        return ResponseEntity.ok(filtered);
    }
}
