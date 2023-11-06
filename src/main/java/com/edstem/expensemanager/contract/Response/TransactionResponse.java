package com.edstem.expensemanager.contract.Response;

import com.edstem.expensemanager.constant.Type;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private String name;
    private Type type;
    private Double amount;
    private LocalDate date;
}
