package com.example.expense_tracker.model;

import com.example.expense_tracker.converter.YearMonthAttributeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.YearMonth;

@Entity
@Table(
        name = "budgets",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"month", "category"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Month is required")
    @Convert(converter = YearMonthAttributeConverter.class)
    @Column(nullable = false)
    private YearMonth month;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    @Column(nullable = false)
    private Double amount;

    @Column(nullable = true)
    private String category;
}
