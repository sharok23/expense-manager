package com.edstem.expensemanager.service;

import com.edstem.expensemanager.contract.Request.CategoryRequest;
import com.edstem.expensemanager.contract.Response.CategoryResponse;
import com.edstem.expensemanager.model.Category;
import com.edstem.expensemanager.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryResponse createCategories(CategoryRequest request) {
        Category song = modelMapper.map(request, Category.class);
        Category savedSong = categoryRepository.save(song);
        return modelMapper.map(savedSong, CategoryResponse.class);
    }

    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }
}
