package com.edstem.expensemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import com.edstem.expensemanager.contract.Request.TransactionRequest;
import com.edstem.expensemanager.contract.Response.AllTransactionResponse;
import com.edstem.expensemanager.contract.Response.TransactionResponse;
import com.edstem.expensemanager.model.Categories;
import com.edstem.expensemanager.model.Transaction;
import com.edstem.expensemanager.repository.CategoriesRepository;
import com.edstem.expensemanager.repository.TransactionRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class TransactionServiceTest {
    private TransactionRepository transactionRepository;
    private CategoriesRepository categoriesRepository;
    private TransactionService transactionService;
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        categoriesRepository = Mockito.mock(CategoriesRepository.class);
        modelMapper = new ModelMapper();
        transactionService =
                new TransactionService(transactionRepository, categoriesRepository, modelMapper);
    }

    @Test
    void testCreateTransaction() {
        Categories category = new Categories(1L, Type.INVESTMENT, Color.YELLOW);

        when(categoriesRepository.findByType(any())).thenReturn(Optional.of(category));

        Transaction transaction =
                Transaction.builder()
                        .name("Test Transaction")
                        .type(Type.INVESTMENT)
                        .amount(100.0)
                        .date(LocalDate.now())
                        .categories(category)
                        .build();

        when(transactionRepository.save(any())).thenReturn(transaction);

        TransactionRequest request = new TransactionRequest();
        request.setName("Test Transaction");
        request.setType(Type.INVESTMENT);
        request.setAmount(100.0);

        TransactionResponse response = transactionService.createTransaction(request);

        assertEquals(response.getName(), "Test Transaction");
        assertEquals(response.getType(), Type.INVESTMENT);
        assertEquals(response.getAmount(), 100.0);
    }

    @Test
    void testGetTransactions() {
        Transaction transaction =
                Transaction.builder()
                        .name("Test Transaction")
                        .type(Type.SAVINGS)
                        .amount(200.0)
                        .date(LocalDate.now())
                        .categories(new Categories())
                        .build();

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        when(transactionRepository.findAll()).thenReturn(transactionList);

        List<TransactionResponse> responseList = transactionService.getTransactions();

        assertEquals(responseList.size(), 1);
        assertEquals(responseList.get(0).getName(), "Test Transaction");
        assertEquals(responseList.get(0).getType(), Type.SAVINGS);
        assertEquals(responseList.get(0).getAmount(), 200.0);
    }

    @Test
    void testDeleteTransactionById() {
        Transaction transaction =
                Transaction.builder()
                        .name("Test Transaction")
                        .type(Type.SAVINGS)
                        .amount(100.0)
                        .date(LocalDate.now())
                        .categories(new Categories())
                        .build();

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        String response = transactionService.deleteTransactionById(1L);

        assertEquals(response, "Transaction Test Transaction has been deleted");
    }

    @Test
    void testGetTransactionsWithColor() {
        Categories category = new Categories(1L, Type.INVESTMENT, Color.YELLOW);

        Transaction transaction =
                Transaction.builder()
                        .name("Test Transaction")
                        .type(Type.INVESTMENT)
                        .amount(100.0)
                        .date(LocalDate.now())
                        .categories(category)
                        .build();

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        when(transactionRepository.findAll()).thenReturn(transactionList);

        List<AllTransactionResponse> responseList = transactionService.getTransactionsWithColor();

        assertEquals(responseList.size(), 1);
        assertEquals(responseList.get(0).getName(), "Test Transaction");
        assertEquals(responseList.get(0).getType(), Type.INVESTMENT);
        assertEquals(responseList.get(0).getAmount(), 100.0);
        assertEquals(responseList.get(0).getColor(), Color.YELLOW);
    }
}
