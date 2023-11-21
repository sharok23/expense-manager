package com.edstem.expensemanager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import com.edstem.expensemanager.contract.Request.TransactionRequest;
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
    void testGetTransactionsWithColor() throws Exception {
        Long userId = 1L;

        TransactionResponse transactionResponse1 =
                TransactionResponse.builder()
                        .id(1L)
                        .name("Transaction1")
                        .type(Type.Expense)
                        .amount(100.0)
                        .color(Color.RED)
                        .date(LocalDate.now())
                        .user(userId)
                        .build();

        TransactionResponse transactionResponse2 =
                TransactionResponse.builder()
                        .id(2L)
                        .name("Transaction2")
                        .type(Type.Investment)
                        .amount(200.0)
                        .color(Color.LIME)
                        .date(LocalDate.now())
                        .user(userId)
                        .build();

        List<TransactionResponse> expectedResponse =
                Arrays.asList(transactionResponse1, transactionResponse2);

        when(transactionService.getTransactionsWithColor(anyLong())).thenReturn(expectedResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(
                        get("/v1/transaction/labels")
                                .param("userId", String.valueOf(userId))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
    }
}
