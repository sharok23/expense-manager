package com.edstem.expensemanager.service;

import com.edstem.expensemanager.contract.Request.ListTransactionRequest;
import com.edstem.expensemanager.contract.Request.TransactionRequest;
import com.edstem.expensemanager.contract.Response.TransactionListResponse;
import com.edstem.expensemanager.contract.Response.TransactionResponse;
import com.edstem.expensemanager.exception.EntityNotFoundException;
import com.edstem.expensemanager.model.Category;
import com.edstem.expensemanager.model.Transaction;
import com.edstem.expensemanager.model.User;
import com.edstem.expensemanager.repository.CategoryRepository;
import com.edstem.expensemanager.repository.TransactionRepository;
import com.edstem.expensemanager.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TransactionResponse createTransaction(TransactionRequest request, Long userId) {
        Category category =
                categoryRepository
                        .findByType(request.getType())
                        .orElseThrow(() -> new EntityNotFoundException("category"));

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("user", userId));

        Transaction transaction =
                Transaction.builder()
                        .name(request.getName())
                        .amount(request.getAmount())
                        .category(category)
                        .date(request.getDate())
                        .user(user)
                        .build();
        transaction = transactionRepository.save(transaction);

        TransactionResponse response =
                TransactionResponse.builder()
                        .id(transaction.getId())
                        .name(transaction.getName())
                        .type(transaction.getCategory().getType())
                        .amount(transaction.getAmount())
                        .color(transaction.getCategory().getColor())
                        .date(transaction.getDate())
                        .user(transaction.getUser().getId())
                        .build();

        return response;
    }

    public String deleteTransactionById(Long userId, Long id) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("user", userId));
        Transaction transaction =
                transactionRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("transaction", id));
        if (!transaction.getUser().equals(user)) {
            throw new EntityNotFoundException("transaction");
        }
        transactionRepository.delete(transaction);
        return "Transaction " + transaction.getName() + " has been deleted";
    }

    public TransactionListResponse listTransactions(Long userId, ListTransactionRequest request) {
        Pageable page =
                PageRequest.of(
                        request.getPageNumber(),
                        request.getPageSize(),
                        Sort.by(Sort.Direction.DESC, "date"));

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("user", userId));

        List<TransactionResponse> transactions =
                transactionRepository.findByUser(user, page).stream()
                        .map(
                                transaction ->
                                        TransactionResponse.builder()
                                                .id(transaction.getId())
                                                .name(transaction.getName())
                                                .type(transaction.getCategory().getType())
                                                .amount(transaction.getAmount())
                                                .color(transaction.getCategory().getColor())
                                                .date(transaction.getDate())
                                                .user(transaction.getUser().getId())
                                                .build())
                        .collect(Collectors.toList());

        Long totalTransactions = transactionRepository.countByUser(user);

        return TransactionListResponse.builder()
                .transactions(transactions)
                .totalTransactions(totalTransactions)
                .build();
    }
}
