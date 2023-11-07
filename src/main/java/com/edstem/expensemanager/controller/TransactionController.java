package com.edstem.expensemanager.controller;

import com.edstem.expensemanager.contract.Request.TransactionRequest;
import com.edstem.expensemanager.contract.Response.AllTransactionResponse;
import com.edstem.expensemanager.contract.Response.TransactionResponse;
import com.edstem.expensemanager.service.TransactionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public TransactionResponse createTransaction(@RequestBody TransactionRequest transaction) {
        return transactionService.createTransaction(transaction);
    }

    @GetMapping
    public List<TransactionResponse> getTransactions() {
        return transactionService.getTransactions();
    }

    @DeleteMapping("/{id}")
    public String deleteTransactionById(@PathVariable Long id) {
        return transactionService.deleteTransactionById(id);
    }

    @GetMapping("/labels")
    public List<AllTransactionResponse> getTransactionsWithColor() {
        return transactionService.getTransactionsWithColor();
    }
}
