package com.edstem.expensemanager.contract.Response;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllTransactionResponse {
    private Long id;
    private String name;
    private Type type;
    private Double amount;
    private Color color;
}
