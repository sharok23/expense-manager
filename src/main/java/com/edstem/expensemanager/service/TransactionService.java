package com.edstem.expensemanager.service;

import com.edstem.expensemanager.contract.Request.TransactionRequest;
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
        transaction = transactionRepository.save(transaction);

        TransactionResponse response =
                TransactionResponse.builder()
                        .id(transaction.getId())
                        .name(transaction.getName())
                        .type(transaction.getType())
                        .amount(transaction.getAmount())
                        .color(transaction.getCategory().getColor())
                        .build();

        return response;
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

    public List<TransactionResponse> getTransactionsWithColor() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(
                        transaction -> {
                            TransactionResponse response =
                                    modelMapper.map(transaction, TransactionResponse.class);
                            if (transaction.getCategory() != null) {
                                response.setColor(transaction.getCategory().getColor());
                            }
                            return response;
                        })
                .collect(Collectors.toList());
    }
}
