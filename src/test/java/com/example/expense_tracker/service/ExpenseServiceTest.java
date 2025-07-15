package com.example.expense_tracker.service;

import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest {

    private ExpenseRepository expenseRepository;
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        expenseRepository = mock(ExpenseRepository.class);
        expenseService = new ExpenseService(expenseRepository);
    }

    @Test
    void createExpense_ShouldReturnSavedExpense() {
        Expense expense = new Expense(null, "Lunch", 15.0, LocalDate.now(), "Food");
        Expense savedExpense = new Expense(1L, "Lunch", 15.0, LocalDate.now(), "Food");

        when(expenseRepository.save(expense)).thenReturn(savedExpense);

        Expense result = expenseService.createExpense(expense);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Lunch", result.getTitle());
        verify(expenseRepository, times(1)).save(expense);
    }

    @Test
    void getAllExpenses_ShouldReturnListOfExpenses() {
        List<Expense> expenses = Arrays.asList(
                new Expense(1L, "Lunch", 15.0, LocalDate.now(), "Food"),
                new Expense(2L, "Taxi", 20.0, LocalDate.now(), "Travel")
        );
        when(expenseRepository.findAll()).thenReturn(expenses);

        List<Expense> result = expenseService.getAllExpenses();

        assertEquals(2, result.size());
        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    void filterExpenses_ShouldReturnFilteredExpenses() {
        String category = "Travel";
        LocalDate startDate = LocalDate.of(2025, 7, 1);
        LocalDate endDate = LocalDate.of(2025, 7, 31);

        List<Expense> filteredExpenses = Arrays.asList(
                new Expense(2L, "Taxi", 20.0, LocalDate.of(2025, 7, 10), "Travel")
        );

        when(expenseRepository.filterExpenses(category, startDate, endDate)).thenReturn(filteredExpenses);

        List<Expense> result = expenseService.filterExpenses(category, startDate, endDate);

        assertEquals(1, result.size());
        assertEquals("Taxi", result.get(0).getTitle());
        verify(expenseRepository, times(1)).filterExpenses(category, startDate, endDate);
    }
}
