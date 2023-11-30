package com.edstem.expensemanager.contract.Response;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private String name;
    private Type type;
    private Double amount;
    private Color color;
    private LocalDate date;
    private Long user;
}
