package com.edstem.expensemanager.contract.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private String name;
    private String type;
    private Double amount;
}
