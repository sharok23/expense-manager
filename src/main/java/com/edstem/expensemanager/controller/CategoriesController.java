package com.edstem.expensemanager.controller;

import com.edstem.expensemanager.contract.Request.CategoriesRequest;
import com.edstem.expensemanager.contract.Response.CategoriesResponse;
import com.edstem.expensemanager.service.CategoriesService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoriesService categoriesService;

    @PostMapping
    public CategoriesResponse createCategories(@RequestBody CategoriesRequest request) {
        return categoriesService.createCategories(request);
    }

    @GetMapping
    public List<CategoriesResponse> getCategories() {
        return categoriesService.getCategories();
    }
}
