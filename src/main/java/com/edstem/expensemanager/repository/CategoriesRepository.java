package com.edstem.expensemanager.repository;

import com.edstem.expensemanager.constant.Type;
import com.edstem.expensemanager.model.Categories;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    Optional<Object> findByType(Type type);
}
