package com.edstem.expensemanager.contract.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ListTransactionRequest {
    private int pageNumber;
    private int pageSize;
}
