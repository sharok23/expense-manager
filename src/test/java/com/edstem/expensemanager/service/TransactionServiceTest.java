//package com.edstem.expensemanager.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//import com.edstem.expensemanager.constant.Color;
//import com.edstem.expensemanager.constant.Type;
//import com.edstem.expensemanager.contract.Request.TransactionRequest;
//import com.edstem.expensemanager.contract.Response.TransactionResponse;
//import com.edstem.expensemanager.model.Category;
//import com.edstem.expensemanager.model.Transaction;
//import com.edstem.expensemanager.repository.CategoryRepository;
//import com.edstem.expensemanager.repository.TransactionRepository;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//
//public class TransactionServiceTest {
//    private TransactionRepository transactionRepository;
//    private CategoryRepository categoryRepository;
//    private TransactionService transactionService;
//    private ModelMapper modelMapper;
//
//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.openMocks(this);
//        transactionRepository = Mockito.mock(TransactionRepository.class);
//        categoryRepository = Mockito.mock(CategoryRepository.class);
//        modelMapper = new ModelMapper();
//        transactionService =
//                new TransactionService(transactionRepository, categoryRepository, modelMapper);
//    }
//
//    @Test
//    void testCreateTransaction() {
//        Category category = new Category(1L, Type.Investment, Color.YELLOW);
//
//        when(categoryRepository.findByType(any())).thenReturn(Optional.of(category));
//
//        Transaction transaction =
//                Transaction.builder()
//                        .name("Test Transaction")
//                        .type(Type.Investment)
//                        .amount(100.0)
//                        .category(category)
//                        .build();
//
//        when(transactionRepository.save(any())).thenReturn(transaction);
//
//        TransactionRequest request = new TransactionRequest();
//        request.setName("Test Transaction");
//        request.setType(Type.Investment);
//        request.setAmount(100.0);
//
//        TransactionResponse response = transactionService.createTransaction(request);
//
//        assertEquals(response.getName(), "Test Transaction");
//        assertEquals(response.getType(), Type.Investment);
//        assertEquals(response.getAmount(), 100.0);
//    }
//
//    @Test
//    void testDeleteTransactionById() {
//        Transaction transaction =
//                Transaction.builder()
//                        .name("Test Transaction")
//                        .type(Type.Savings)
//                        .amount(100.0)
//                        .category(new Category())
//                        .build();
//
//        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
//
//        String response = transactionService.deleteTransactionById(1L);
//
//        assertEquals(response, "Transaction Test Transaction has been deleted");
//    }
//
//    @Test
//    void testGetTransactionsWithColor() {
//        Category category = new Category(1L, Type.Expense, Color.RED);
//
//        Transaction transaction =
//                Transaction.builder()
//                        .name("Test Transaction")
//                        .type(Type.Expense)
//                        .amount(100.0)
//                        .category(category)
//                        .build();
//
//        List<Transaction> transactionList = new ArrayList<>();
//        transactionList.add(transaction);
//
//        when(transactionRepository.findAll()).thenReturn(transactionList);
//
//        List<TransactionResponse> responseList = transactionService.getTransactionsWithColor();
//
//        assertEquals(responseList.size(), 1);
//        assertEquals(responseList.get(0).getName(), "Test Transaction");
//        assertEquals(responseList.get(0).getType(), Type.Expense);
//        assertEquals(responseList.get(0).getAmount(), 100.0);
//        assertEquals(responseList.get(0).getColor(), Color.RED);
//    }
//}
