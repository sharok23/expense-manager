package com.edstem.expensemanager.repository;

import com.edstem.expensemanager.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

}
