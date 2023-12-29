package com.edstem.expensemanager.contract.Response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TransactionListResponse {
    private List<TransactionResponse> transactions;
    private Long totalTransactions;
    private int currentPage;
    private int totalPages;
}
