package com.edstem.expensemanager.service;

import com.edstem.expensemanager.contract.Request.CategoriesRequest;
import com.edstem.expensemanager.contract.Response.CategoriesResponse;
import com.edstem.expensemanager.model.Categories;
import com.edstem.expensemanager.repository.CategoriesRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final ModelMapper modelMapper;

    public CategoriesResponse createCategories(CategoriesRequest request) {
        Categories song = modelMapper.map(request, Categories.class);
        Categories savedSong = categoriesRepository.save(song);
        return modelMapper.map(savedSong, CategoriesResponse.class);
    }

    public List<CategoriesResponse> getCategories() {
        List<Categories> categories = categoriesRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoriesResponse.class))
                .collect(Collectors.toList());
    }
}
