package com.example.expense_tracker.service;

import com.example.expense_tracker.model.Budget;
import com.example.expense_tracker.repository.BudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BudgetServiceTest {

    private BudgetRepository budgetRepository;
    private BudgetService budgetService;

    @BeforeEach
    void setUp() {
        budgetRepository = mock(BudgetRepository.class);
        budgetService = new BudgetService(budgetRepository);
    }

    @Test
    void saveBudget_shouldCreateNewBudget_whenNoExistingBudgetExists() {
        Budget budget = new Budget(null, YearMonth.of(2025, 7), 500.0, "Travel");

        when(budgetRepository.findByCategoryAndMonth("Travel", YearMonth.of(2025, 7)))
                .thenReturn(Optional.empty());

        when(budgetRepository.save(budget)).thenReturn(budget);

        Budget saved = budgetService.saveBudget(budget);

        assertThat(saved).isEqualTo(budget);
        verify(budgetRepository).save(budget);
    }

    @Test
    void saveBudget_shouldUpdateExistingBudget_whenBudgetExists() {
        Budget existing = new Budget(1L, YearMonth.of(2025, 7), 300.0, "Food");
        Budget incoming = new Budget(null, YearMonth.of(2025, 7), 450.0, "Food");

        when(budgetRepository.findByCategoryAndMonth("Food", YearMonth.of(2025, 7)))
                .thenReturn(Optional.of(existing));

        ArgumentCaptor<Budget> captor = ArgumentCaptor.forClass(Budget.class);
        when(budgetRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Budget saved = budgetService.saveBudget(incoming);

        verify(budgetRepository).save(captor.capture());
        Budget captured = captor.getValue();

        assertThat(captured.getId()).isEqualTo(existing.getId());
        assertThat(captured.getAmount()).isEqualTo(450.0);
    }

    @Test
    void getAllBudgets_shouldReturnListOfBudgets() {
        List<Budget> expected = List.of(
                new Budget(1L, YearMonth.of(2025, 7), 200.0, "Health"),
                new Budget(2L, YearMonth.of(2025, 7), 300.0, "Food")
        );

        when(budgetRepository.findAll()).thenReturn(expected);

        List<Budget> result = budgetService.getAllBudgets();

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findByCategoryAndMonth_shouldReturnBudgetIfExists() {
        Budget budget = new Budget(1L, YearMonth.of(2025, 7), 150.0, "Utilities");
        when(budgetRepository.findByCategoryAndMonth("Utilities", YearMonth.of(2025, 7)))
                .thenReturn(Optional.of(budget));

        Optional<Budget> found = budgetService.findByCategoryAndMonth("Utilities", YearMonth.of(2025, 7));

        assertThat(found).isPresent();
        assertThat(found.get().getAmount()).isEqualTo(150.0);
    }
}
