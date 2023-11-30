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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class TransactionServiceTest {
    private TransactionRepository transactionRepository;
    private CategoryRepository categoryRepository;
    private TransactionService transactionService;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        categoryRepository = Mockito.mock(CategoryRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        transactionService =
                new TransactionService(
                        transactionRepository, categoryRepository, userRepository, modelMapper);
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

        Transaction transaction =
                Transaction.builder()
                        .id(1L)
                        .name("Transaction")
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

        List<Transaction> transactions = Arrays.asList(transaction);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.findByUser(user)).thenReturn(transactions);

        List<TransactionResponse> responseList =
                transactionService.getTransactionsWithColor(userId);

        assertEquals(1, responseList.size());

        TransactionResponse response = responseList.get(0);
        assertEquals(transaction.getId(), response.getId());
        assertEquals(transaction.getName(), response.getName());
        assertEquals(transaction.getType(), response.getType());
        assertEquals(transaction.getAmount(), response.getAmount());
        assertEquals(transaction.getCategory().getColor(), response.getColor());
        assertEquals(transaction.getDate(), response.getDate());
        assertEquals(transaction.getUser().getId(), response.getUser());
    }

    @Test
    void testGetPageable() {
        Pageable pageable = PageRequest.of(0, 5);

        Transaction transaction = Transaction.builder().id(1L).build();

        List<Transaction> transactionList = Arrays.asList(transaction);
        Page<Transaction> transactionPage =
                new PageImpl<>(transactionList, pageable, transactionList.size());

        when(transactionRepository.findAll(pageable)).thenReturn(transactionPage);

        TransactionResponse transactionResponse = TransactionResponse.builder().id(1L).build();

        List<TransactionResponse> responseList = Arrays.asList(transactionResponse);
        Page<TransactionResponse> responsePage =
                new PageImpl<>(responseList, pageable, responseList.size());

        when(modelMapper.map(transaction, TransactionResponse.class))
                .thenReturn(transactionResponse);

        Page<TransactionResponse> actualResponsePage = transactionService.getPageable(pageable);

        assertEquals(responsePage.getTotalElements(), actualResponsePage.getTotalElements());
        assertEquals(responsePage.getNumber(), actualResponsePage.getNumber());
        assertEquals(responsePage.getSize(), actualResponsePage.getSize());

        for (int i = 0; i < actualResponsePage.getContent().size(); i++) {
            assertEquals(
                    responsePage.getContent().get(i).getId(),
                    actualResponsePage.getContent().get(i).getId());
        }
    }
}
