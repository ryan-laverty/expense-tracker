package com.example.expense_tracker.controller;

import com.example.expense_tracker.model.Budget;
import com.example.expense_tracker.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    /**
     * POST /api/budgets
     * Create or update a budget.
     *
     * @param budget Budget object to create or update.
     * @return Saved budget.
     */
    @PostMapping
    public ResponseEntity<Budget> saveBudget(@Valid @RequestBody Budget budget) {
        Budget savedBudget = budgetService.saveBudget(budget);
        return new ResponseEntity<>(savedBudget, HttpStatus.CREATED);
    }

    /**
     * GET /api/budgets
     * Retrieve all budgets.
     *
     * @return List of budgets.
     */
    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetService.getAllBudgets();
    }
}
