package com.edstem.expensemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import com.edstem.expensemanager.contract.Response.CategoryResponse;
import com.edstem.expensemanager.model.Category;
import com.edstem.expensemanager.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class CategoryServiceTest {
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        categoryRepository = Mockito.mock(CategoryRepository.class);
        modelMapper = new ModelMapper();
        categoryService = new CategoryService(categoryRepository, modelMapper);
    }

    @Test
    void testGetCategories() {
        Category entity = new Category(1L, Type.Investment, Color.LIME);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(entity);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<CategoryResponse> responseList = categoryService.getCategories();

        assertEquals(responseList.size(), 1);
        assertEquals(responseList.get(0).getId(), 1L);
        assertEquals(responseList.get(0).getType(), Type.Investment.toString());
        assertEquals(responseList.get(0).getColor(), Color.LIME.toString());
    }

    @Test
    public void testInitializeValue() {
        when(categoryRepository.findByType(any(Type.class))).thenReturn(Optional.empty());

        categoryService.initializeValue();

        verify(categoryRepository, times(1)).findByType(Type.Investment);
        verify(categoryRepository, times(1)).findByType(Type.Savings);
        verify(categoryRepository, times(1)).findByType(Type.Expense);
    }
}
