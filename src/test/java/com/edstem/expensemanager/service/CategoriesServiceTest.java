package com.edstem.expensemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import com.edstem.expensemanager.contract.Request.CategoriesRequest;
import com.edstem.expensemanager.contract.Response.CategoriesResponse;
import com.edstem.expensemanager.model.Categories;
import com.edstem.expensemanager.repository.CategoriesRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class CategoriesServiceTest {
    private CategoriesRepository categoriesRepository;
    private CategoriesService categoriesService;
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        categoriesRepository = Mockito.mock(CategoriesRepository.class);
        modelMapper = new ModelMapper();
        categoriesService = new CategoriesService(categoriesRepository, modelMapper);
    }

    @Test
    void testCreateCategories() {
        Categories entity = new Categories();
        CategoriesRequest request = new CategoriesRequest();
        CategoriesResponse expectedResponse = new CategoriesResponse();

        when(categoriesRepository.save(any())).thenReturn(entity);
        CategoriesResponse actualResponse = categoriesService.createCategories(request);

        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    void testGetCategories() {
        Categories entity = new Categories(1L, Type.EXPENSE, Color.RED);

        List<Categories> categoriesList = new ArrayList<>();
        categoriesList.add(entity);

        when(categoriesRepository.findAll()).thenReturn(categoriesList);

        List<CategoriesResponse> responseList = categoriesService.getCategories();

        assertEquals(responseList.size(), 1);
        assertEquals(responseList.get(0).getId(), 1L);
        assertEquals(responseList.get(0).getType(), Type.EXPENSE);
        assertEquals(responseList.get(0).getColor(), Color.RED);
    }
}
