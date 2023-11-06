package com.edstem.expensemanager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.expensemanager.contract.Request.CategoriesRequest;
import com.edstem.expensemanager.contract.Response.CategoriesResponse;
import com.edstem.expensemanager.service.CategoriesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
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
public class CategoriesControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private CategoriesService categoriesService;

    @Test
    void testCreateCategories() throws Exception {
        CategoriesRequest request = new CategoriesRequest();
        CategoriesResponse expectedResponse = new CategoriesResponse();

        when(categoriesService.createCategories(any(CategoriesRequest.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testGetCategories() throws Exception {
        List<CategoriesResponse> expectedResponse = new ArrayList<>();

        when(categoriesService.getCategories()).thenReturn(expectedResponse);

        mockMvc.perform(get("/v1/categories").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
}
