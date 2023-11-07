package com.edstem.expensemanager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.expensemanager.contract.Request.CategoryRequest;
import com.edstem.expensemanager.contract.Response.CategoryResponse;
import com.edstem.expensemanager.service.CategoryService;
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
public class CategoryControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private CategoryService categoryService;

    @Test
    void testCreateCategories() throws Exception {
        CategoryRequest request = new CategoryRequest();
        CategoryResponse expectedResponse = new CategoryResponse();

        when(categoryService.createCategories(any(CategoryRequest.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/v1/category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testGetCategories() throws Exception {
        List<CategoryResponse> expectedResponse = new ArrayList<>();

        when(categoryService.getCategories()).thenReturn(expectedResponse);

        mockMvc.perform(get("/v1/category").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
}
