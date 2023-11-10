package com.edstem.expensemanager.service;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import com.edstem.expensemanager.contract.Response.CategoryResponse;
import com.edstem.expensemanager.model.Category;
import com.edstem.expensemanager.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    public void initializeValue() {
        saveCategory(Type.Investment, Color.YELLOW);
        saveCategory(Type.Savings, Color.BLUE);
        saveCategory(Type.Expense, Color.RED);
    }

    private void saveCategory(Type type, Color color) {
        Optional<Category> optionalCategory = categoryRepository.findByType(type);
        if (!optionalCategory.isPresent()) {
            Category category = Category.builder().type(type).color(color).build();
            categoryRepository.save(category);
        }
    }
}
