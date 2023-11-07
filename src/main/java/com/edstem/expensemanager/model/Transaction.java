package com.edstem.expensemanager.model;

import com.edstem.expensemanager.constant.Type;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    private Double amount;
    private LocalDate date;

    @ManyToOne
    private Categories categories;
}
