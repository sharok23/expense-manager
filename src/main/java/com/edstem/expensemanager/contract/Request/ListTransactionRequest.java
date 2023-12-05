package com.edstem.expensemanager.contract.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListTransactionRequest {
    private int pageNumber;
    private int pageSize;
}
