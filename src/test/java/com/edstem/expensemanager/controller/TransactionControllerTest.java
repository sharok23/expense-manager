//package com.edstem.expensemanager.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.edstem.expensemanager.contract.Request.TransactionRequest;
//import com.edstem.expensemanager.contract.Response.TransactionResponse;
//import com.edstem.expensemanager.service.TransactionService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class TransactionControllerTest {
//
//    @Autowired private MockMvc mockMvc;
//
//    @MockBean private TransactionService transactionService;
//
//    @Test
//    void testCreateTransaction() throws Exception {
//        TransactionRequest request = new TransactionRequest();
//        TransactionResponse expectedResponse = new TransactionResponse();
//
//        when(transactionService.createTransaction(any(TransactionRequest.class)))
//                .thenReturn(expectedResponse);
//
//        mockMvc.perform(
//                        post("/v1/transaction")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(new ObjectMapper().writeValueAsString(request)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
//    }
//
//    @Test
//    void testDeleteTransactionById() throws Exception {
//        Long id = 1L;
//        String expectedResponse = "Transaction Home rent deleted successfully";
//
//        when(transactionService.deleteTransactionById(any(Long.class)))
//                .thenReturn(expectedResponse);
//
//        mockMvc.perform(delete("/v1/transaction/" + id).contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(expectedResponse));
//    }
//
//    @Test
//    void testGetTransactionsWithColor() throws Exception {
//        List<TransactionResponse> expectedResponse = new ArrayList<>();
//
//        when(transactionService.getTransactionsWithColor()).thenReturn(expectedResponse);
//
//        mockMvc.perform(get("/v1/transaction/labels").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
//    }
//}
