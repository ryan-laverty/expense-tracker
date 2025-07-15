package com.example.expense_tracker.controller;

import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseRepository repo;

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return repo.save(expense);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return repo.findAll();
    }
}
