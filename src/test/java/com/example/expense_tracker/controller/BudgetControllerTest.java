package com.example.expense_tracker.controller;

import com.example.expense_tracker.model.Budget;
import com.example.expense_tracker.service.BudgetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.YearMonth;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BudgetController.class)
class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetService budgetService;

    @Autowired
    private ObjectMapper objectMapper;

    private Budget sampleBudget;

    @BeforeEach
    void setUp() {
        sampleBudget = new Budget(1L, YearMonth.of(2025, 7), 500.0, "Travel");
    }

    @Test
    void saveBudget_shouldReturnCreatedBudget() throws Exception {
        when(budgetService.saveBudget(any(Budget.class))).thenReturn(sampleBudget);

        mockMvc.perform(post("/api/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleBudget)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleBudget.getId()))
                .andExpect(jsonPath("$.category").value(sampleBudget.getCategory()));

        verify(budgetService).saveBudget(any(Budget.class));
    }

    @Test
    void getAllBudgets_shouldReturnBudgetList() throws Exception {
        when(budgetService.getAllBudgets()).thenReturn(List.of(sampleBudget));

        mockMvc.perform(get("/api/budgets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].category").value(sampleBudget.getCategory()));

        verify(budgetService).getAllBudgets();
    }
}
