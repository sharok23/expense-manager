package com.edstem.expensemanager.service;

import com.edstem.expensemanager.contract.Request.TransactionRequest;
import com.edstem.expensemanager.contract.Response.AllTransactionResponse;
import com.edstem.expensemanager.contract.Response.TransactionResponse;
import com.edstem.expensemanager.model.Category;
import com.edstem.expensemanager.model.Transaction;
import com.edstem.expensemanager.repository.CategoryRepository;
import com.edstem.expensemanager.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public TransactionResponse createTransaction(TransactionRequest request) {
        Category category =
                (Category)
                        categoryRepository
                                .findByType(request.getType())
                                .orElseThrow(
                                        () ->
                                                new RuntimeException(
                                                        "Category not found for type "
                                                                + request.getType()));
        Transaction transaction =
                Transaction.builder()
                        .name(request.getName())
                        .type(request.getType())
                        .amount(request.getAmount())
                        .category(category)
                        .build();
        Transaction savedTransaction = transactionRepository.save(transaction);
        return modelMapper.map(savedTransaction, TransactionResponse.class);
    }

    public List<TransactionResponse> getTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionResponse.class))
                .collect(Collectors.toList());
    }

    public String deleteTransactionById(Long id) {
        Transaction transaction =
                transactionRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Transaction not found with id " + id));
        transactionRepository.delete(transaction);
        return "Transaction " + transaction.getName() + " has been deleted";
    }

    public List<AllTransactionResponse> getTransactionsWithColor() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(
                        transaction -> {
                            AllTransactionResponse response =
                                    modelMapper.map(transaction, AllTransactionResponse.class);
                            if (transaction.getCategory() != null) {
                                response.setColor(transaction.getCategory().getColor());
                            }
                            return response;
                        })
                .collect(Collectors.toList());
    }
}
