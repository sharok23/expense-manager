package com.edstem.expensemanager.contract.Response;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import com.edstem.expensemanager.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TransactionResponse {
    private Long id;
    private String name;
    private Type type;
    private Double amount;
    private Color color;
    private LocalDate date;
    private User user;
}
