package com.example.expense_tracker.controller;

import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private ObjectMapper objectMapper;

    private Expense expense1;

    @BeforeEach
    void setup() {
        expense1 = new Expense(1L, "Lunch", 15.0, LocalDate.now(), "Food");
    }

    @Test
    void createExpense_ShouldReturnCreatedExpense() throws Exception {
        Mockito.when(expenseService.createExpense(any(Expense.class))).thenReturn(expense1);

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expense1.getId()))
                .andExpect(jsonPath("$.title").value(expense1.getTitle()));
    }

    @Test
    void getAllExpenses_ShouldReturnList() throws Exception {
        Mockito.when(expenseService.getAllExpenses()).thenReturn(Arrays.asList(expense1));

        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value(expense1.getTitle()));
    }

    @Test
    void filterExpenses_ShouldReturnFilteredList() throws Exception {
        Mockito.when(expenseService.filterExpenses(eq("Food"), any(), any())).thenReturn(Arrays.asList(expense1));

        mockMvc.perform(get("/api/expenses/filter")
                        .param("category", "Food"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].category").value("Food"));
    }
}
