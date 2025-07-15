package com.example.expense_tracker.controller;

import com.example.expense_tracker.kafka.KafkaProducer;
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
    private final KafkaProducer kafkaProducer;

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        Expense saved = repo.save(expense);
        kafkaProducer.sendExpense("expenses", saved);
        return saved;
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return repo.findAll();
    }
}
