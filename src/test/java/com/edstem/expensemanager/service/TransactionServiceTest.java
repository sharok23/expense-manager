package com.edstem.expensemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import com.edstem.expensemanager.contract.Request.TransactionRequest;
import com.edstem.expensemanager.contract.Response.TransactionResponse;
import com.edstem.expensemanager.model.Category;
import com.edstem.expensemanager.model.Transaction;
import com.edstem.expensemanager.model.User;
import com.edstem.expensemanager.repository.CategoryRepository;
import com.edstem.expensemanager.repository.TransactionRepository;
import com.edstem.expensemanager.repository.UserRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
    void testCreateTransaction() {
        TransactionRequest request = new TransactionRequest();
        request.setName("TestTransaction");
        request.setType(Type.Expense);
        request.setAmount(100.0);
        request.setDate(LocalDate.now());

        Long userId = 1L;

        Category category = new Category(1L, Type.Expense, Color.RED);

        User user = User.builder().id(userId).name("TestUser").build();

        when(categoryRepository.findByType(request.getType())).thenReturn(Optional.of(category));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(
                        invocation -> {
                            Transaction savedTransaction =
                                    Transaction.builder()
                                            .id(1L)
                                            .name(request.getName())
                                            .type(request.getType())
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
        Long transactionId = 2L;
        String transactionName = "TestTransaction";

        User user = User.builder().id(userId).name("TestUser").build();

        Transaction transaction =
                Transaction.builder()
                        .id(transactionId)
                        .name(transactionName)
                        .type(Type.Investment)
                        .amount(100.0)
                        .date(LocalDate.now())
                        .user(user)
                        .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        String result = transactionService.deleteTransactionById(userId, transactionId);

        verify(transactionRepository, times(1)).delete(transaction);
        assertEquals("Transaction " + transactionName + " has been deleted", result);
    }

    @Test
    void testGetTransactionsWithColor() {
        Long userId = 1L;

        User user = User.builder().id(userId).name("TestUser").build();

        Transaction transaction1 =
                Transaction.builder()
                        .id(1L)
                        .name("Transaction1")
                        .type(Type.Investment)
                        .amount(100.0)
                        .date(LocalDate.now())
                        .user(user)
                        .category(
                                Category.builder()
                                        .id(1L)
                                        .type(Type.Investment)
                                        .color(Color.LIME)
                                        .build())
                        .build();

        Transaction transaction2 =
                Transaction.builder()
                        .id(2L)
                        .name("Transaction2")
                        .type(Type.Savings)
                        .amount(200.0)
                        .date(LocalDate.now())
                        .user(user)
                        .category(
                                Category.builder()
                                        .id(2L)
                                        .type(Type.Savings)
                                        .color(Color.BLUE)
                                        .build())
                        .build();

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.findByUser(user)).thenReturn(transactions);

        List<TransactionResponse> responseList =
                transactionService.getTransactionsWithColor(userId);

        assertEquals(2, responseList.size());

        TransactionResponse response1 = responseList.get(0);
        assertEquals(transaction1.getId(), response1.getId());
        assertEquals(transaction1.getName(), response1.getName());
        assertEquals(transaction1.getType(), response1.getType());
        assertEquals(transaction1.getAmount(), response1.getAmount());
        assertEquals(transaction1.getCategory().getColor(), response1.getColor());
        assertEquals(transaction1.getDate(), response1.getDate());
        assertEquals(transaction1.getUser().getId(), response1.getUser());

        TransactionResponse response2 = responseList.get(1);
        assertEquals(transaction2.getId(), response2.getId());
        assertEquals(transaction2.getName(), response2.getName());
        assertEquals(transaction2.getType(), response2.getType());
        assertEquals(transaction2.getAmount(), response2.getAmount());
        assertEquals(transaction2.getCategory().getColor(), response2.getColor());
        assertEquals(transaction2.getDate(), response2.getDate());
        assertEquals(transaction2.getUser().getId(), response2.getUser());
    }

    @Test
    void testGetTransactionsByDate() {
        Long userId = 1L;
        LocalDate date = LocalDate.now();

        User user = User.builder().id(userId).name("TestUser").build();

        Transaction transaction1 =
                Transaction.builder()
                        .id(1L)
                        .name("Transaction1")
                        .type(Type.Expense)
                        .amount(100.0)
                        .date(date)
                        .user(user)
                        .category(
                                Category.builder()
                                        .id(1L)
                                        .type(Type.Expense)
                                        .color(Color.RED)
                                        .build())
                        .build();

        Transaction transaction2 =
                Transaction.builder()
                        .id(2L)
                        .name("Transaction2")
                        .type(Type.Investment)
                        .amount(200.0)
                        .date(date)
                        .user(user)
                        .category(
                                Category.builder()
                                        .id(2L)
                                        .type(Type.Investment)
                                        .color(Color.LIME)
                                        .build())
                        .build();

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.findAllByUserAndDate(user, date)).thenReturn(transactions);

        List<TransactionResponse> responseList =
                transactionService.getTransactionsByDate(userId, date);

        assertEquals(2, responseList.size());

        TransactionResponse response1 = responseList.get(0);
        assertEquals(transaction1.getId(), response1.getId());
        assertEquals(transaction1.getName(), response1.getName());
        assertEquals(transaction1.getType(), response1.getType());
        assertEquals(transaction1.getAmount(), response1.getAmount());
        assertEquals(transaction1.getCategory().getColor(), response1.getColor());
        assertEquals(transaction1.getDate(), response1.getDate());
        assertEquals(transaction1.getUser().getId(), response1.getUser());

        TransactionResponse response2 = responseList.get(1);
        assertEquals(transaction2.getId(), response2.getId());
        assertEquals(transaction2.getName(), response2.getName());
        assertEquals(transaction2.getType(), response2.getType());
        assertEquals(transaction2.getAmount(), response2.getAmount());
        assertEquals(transaction2.getCategory().getColor(), response2.getColor());
        assertEquals(transaction2.getDate(), response2.getDate());
        assertEquals(transaction2.getUser().getId(), response2.getUser());
    }
}
