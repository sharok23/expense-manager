package com.edstem.expensemanager.repository;

import com.edstem.expensemanager.constant.Type;
import com.edstem.expensemanager.model.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByType(Type type);
}
