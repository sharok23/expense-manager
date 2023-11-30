package com.edstem.expensemanager.service;

import com.edstem.expensemanager.contract.Request.TransactionRequest;
import com.edstem.expensemanager.contract.Response.TransactionResponse;
import com.edstem.expensemanager.model.Category;
import com.edstem.expensemanager.model.Transaction;
import com.edstem.expensemanager.model.User;
import com.edstem.expensemanager.repository.CategoryRepository;
import com.edstem.expensemanager.repository.TransactionRepository;
import com.edstem.expensemanager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public TransactionResponse createTransaction(TransactionRequest request, Long userId) {
        Category category =
                categoryRepository
                        .findByType(request.getType())
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Category not found for type "
                                                        + request.getType()));

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "User not found on id " + userId));

        Transaction transaction =
                Transaction.builder()
                        .name(request.getName())
                        .type(request.getType())
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
                        .type(transaction.getType())
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
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "User not found on id " + userId));
        Transaction transaction =
                transactionRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Transaction not found with id " + id));
        if (!transaction.getUser().equals(user)) {
            throw new EntityNotFoundException("The transaction does not belong to the given user");
        }
        transactionRepository.delete(transaction);
        return "Transaction " + transaction.getName() + " has been deleted";
    }

    public List<TransactionResponse> getTransactionsWithColor(Long userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "User not found on id " + userId));
        List<Transaction> transactions = transactionRepository.findByUser(user);
        return transactions.stream()
                .map(
                        transaction -> {
                            TransactionResponse response =
                                    TransactionResponse.builder()
                                            .id(transaction.getId())
                                            .name(transaction.getName())
                                            .type(transaction.getType())
                                            .amount(transaction.getAmount())
                                            .color(transaction.getCategory().getColor())
                                            .date(transaction.getDate())
                                            .user(transaction.getUser().getId())
                                            .build();
                            return response;
                        })
                .collect(Collectors.toList());
    }

    public Page<TransactionResponse> getPageable(Pageable pageable) {
        Page<Transaction> tickets = transactionRepository.findAll(pageable);
        return tickets.map(appList -> modelMapper.map(appList, TransactionResponse.class));
    }
}
