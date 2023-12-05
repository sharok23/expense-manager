package com.edstem.expensemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class TransactionServiceTest {
    private TransactionRepository transactionRepository;
    private CategoryRepository categoryRepository;
    private TransactionService transactionService;
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        categoryRepository = Mockito.mock(CategoryRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        transactionService =
                new TransactionService(transactionRepository, categoryRepository, userRepository);
    }

    @Test
    void testCreateTransactionWithExceptions() {
        TransactionRequest request = new TransactionRequest();
        request.setName("TestTransaction");
        request.setType(Type.Expense);
        request.setAmount(100.0);
        request.setDate(LocalDate.now());

        Long userId = 1L;

        Category category = new Category(1L, Type.Expense, Color.RED);

        User user = User.builder().id(userId).name("TestUser").build();

        when(categoryRepository.findByType(request.getType())).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> transactionService.createTransaction(request, userId));

        when(categoryRepository.findByType(request.getType())).thenReturn(Optional.of(category));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> transactionService.createTransaction(request, userId));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(
                        invocation -> {
                            Transaction savedTransaction =
                                    Transaction.builder()
                                            .id(1L)
                                            .name(request.getName())
                                            .amount(request.getAmount())
                                            .category(category)
                                            .date(request.getDate())
                                            .user(user)
                                            .build();
                            return savedTransaction;
                        });

        TransactionResponse response = transactionService.createTransaction(request, userId);

        assertEquals(1L, response.getId());
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getType(), response.getType());
        assertEquals(request.getAmount(), response.getAmount());
        assertEquals(category.getColor(), response.getColor());
        assertEquals(request.getDate(), response.getDate());
        assertEquals(userId, response.getUser());
    }

    @Test
    void testDeleteTransactionById() {
        Long userId = 1L;
        Long transactionId = 1L;

        User user = User.builder().id(userId).name("TestUser 1").build();
        Transaction transaction =
                Transaction.builder().id(transactionId).name("TestTransaction").user(user).build();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> transactionService.deleteTransactionById(userId, transactionId));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> transactionService.deleteTransactionById(userId, transactionId));

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(userRepository.findById(2L))
                .thenReturn(Optional.of(User.builder().id(2L).name("TestUser 2").build()));
        assertThrows(
                EntityNotFoundException.class,
                () -> transactionService.deleteTransactionById(2L, transactionId));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        String response = transactionService.deleteTransactionById(userId, transactionId);
        assertEquals("Transaction " + transaction.getName() + " has been deleted", response);
    }

    @Test
    void testListTransactions() {
        Long userId = 1L;
        ListTransactionRequest request = new ListTransactionRequest(0, 10);

        User user = User.builder().id(userId).name("TestUser").build();
        Category category = new Category(1L, Type.Expense, Color.RED);
        Transaction transaction =
                Transaction.builder()
                        .id(1L)
                        .name("TestTransaction")
                        .user(user)
                        .category(category)
                        .build();

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> transactionService.listTransactions(userId, request));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.findByUser(
                        user,
                        PageRequest.of(
                                request.getPageNumber(),
                                request.getPageSize(),
                                Sort.by(Sort.Direction.DESC, "date"))))
                .thenReturn(new PageImpl<>(transactions));

        when(transactionRepository.countByUser(user)).thenReturn(1L);

        TransactionListResponse response = transactionService.listTransactions(userId, request);
        assertEquals(1, response.getTransactions().size());
        assertEquals(1L, response.getTotalTransactions());

        TransactionResponse transactionResponse = response.getTransactions().get(0);
        assertEquals(transaction.getId(), transactionResponse.getId());
        assertEquals(transaction.getName(), transactionResponse.getName());
        assertEquals(transaction.getCategory().getType(), transactionResponse.getType());
        assertEquals(transaction.getAmount(), transactionResponse.getAmount());
        assertEquals(transaction.getCategory().getColor(), transactionResponse.getColor());
        assertEquals(transaction.getDate(), transactionResponse.getDate());
        assertEquals(transaction.getUser().getId(), transactionResponse.getUser());
    }
}
