package com.edstem.expensemanager.controller;

import com.edstem.expensemanager.contract.Request.ListTransactionRequest;
import com.edstem.expensemanager.contract.Request.TransactionRequest;
import com.edstem.expensemanager.contract.Response.TransactionResponse;
import com.edstem.expensemanager.service.TransactionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public TransactionResponse createTransaction(
            @RequestBody TransactionRequest transaction, @RequestParam Long userId) {
        return transactionService.createTransaction(transaction, userId);
    }

    @DeleteMapping("/{id}")
    public String deleteTransactionById(@RequestParam Long userId, @PathVariable Long id) {
        return transactionService.deleteTransactionById(userId, id);
    }

    @PostMapping("/list")
    public List<TransactionResponse> listTransactions(
            @RequestParam Long userId, @RequestBody ListTransactionRequest request) {
        return transactionService.listTransactions(userId, request);
    }
}
