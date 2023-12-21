package com.edstem.expensemanager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import com.edstem.expensemanager.contract.Request.ListTransactionRequest;
import com.edstem.expensemanager.contract.Request.TransactionRequest;
import com.edstem.expensemanager.contract.Response.TransactionListResponse;
import com.edstem.expensemanager.contract.Response.TransactionResponse;
import com.edstem.expensemanager.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private TransactionService transactionService;

    @Test
    void testCreateTransaction() throws Exception {
        Long userId = 1L;
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setName("TestTransaction");
        transactionRequest.setType(Type.Expense);
        transactionRequest.setAmount(100.0);

        LocalDate date = LocalDate.now();
        transactionRequest.setDate(date);

        TransactionResponse expectedResponse =
                TransactionResponse.builder()
                        .id(1L)
                        .name("TestTransaction")
                        .type(Type.Expense)
                        .amount(100.0)
                        .color(Color.RED)
                        .date(date)
                        .user(userId)
                        .build();

        when(transactionService.createTransaction(any(TransactionRequest.class), eq(userId)))
                .thenReturn(expectedResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(
                        post("/v1/transaction")
                                .param("userId", String.valueOf(userId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(transactionRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
    }

    @Test
    public void testDeleteTransactionById() throws Exception {
        Long userId = 1L;
        Long transactionId = 1L;

        String expectedResponse = "Transaction deleted successfully";

        when(transactionService.deleteTransactionById(anyLong(), anyLong()))
                .thenReturn(expectedResponse);

        mockMvc.perform(
                        delete("/v1/transaction/" + transactionId)
                                .param("userId", String.valueOf(userId))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    void testListTransactions() throws Exception {
        Long userId = 1L;
        ListTransactionRequest request = new ListTransactionRequest(0, 10);

        TransactionResponse transactionResponse =
                TransactionResponse.builder()
                        .id(1L)
                        .name("Transaction")
                        .type(Type.Expense)
                        .amount(100.0)
                        .color(Color.RED)
                        .date(LocalDate.now())
                        .user(userId)
                        .build();

        List<TransactionResponse> transactionResponses = Arrays.asList(transactionResponse);
        TransactionListResponse expectedResponse =
                new TransactionListResponse(transactionResponses, 1L);

        when(transactionService.listTransactions(anyLong(), any(ListTransactionRequest.class)))
                .thenReturn(expectedResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(
                        post("/v1/transaction/list")
                                .param("userId", String.valueOf(userId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void testByDateListTransactions() throws Exception {
        Long userId = 1L;
        LocalDate dateFrom = LocalDate.now().minusDays(5);
        LocalDate dateTo = LocalDate.now();
        ListTransactionRequest request = new ListTransactionRequest(0, 10);

        TransactionResponse transactionResponse =
                TransactionResponse.builder()
                        .id(1L)
                        .name("Transaction")
                        .type(Type.Expense)
                        .amount(100.0)
                        .color(Color.RED)
                        .date(LocalDate.now())
                        .user(userId)
                        .build();

        List<TransactionResponse> transactionResponses = Arrays.asList(transactionResponse);
        TransactionListResponse expectedResponse =
                new TransactionListResponse(transactionResponses, 1L);

        when(transactionService.byDatelistTransactions(
                        anyLong(),
                        any(LocalDate.class),
                        any(LocalDate.class),
                        any(ListTransactionRequest.class)))
                .thenReturn(expectedResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(
                        post("/v1/transaction/date")
                                .param("userId", String.valueOf(userId))
                                .param("dateFrom", dateFrom.toString())
                                .param("dateTo", dateTo.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
    }
}
