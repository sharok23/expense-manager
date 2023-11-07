package com.edstem.expensemanager.contract.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String type;
    private Double amount;
}
