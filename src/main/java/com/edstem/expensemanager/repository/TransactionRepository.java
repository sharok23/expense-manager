package com.edstem.expensemanager.repository;

import com.edstem.expensemanager.model.Transaction;
import com.edstem.expensemanager.model.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByUser(User user, Pageable pageable);

    Long countByUser(User user);

    List<Transaction> findByUserAndDateBetween(
            User user, LocalDate dateFrom, LocalDate dateTo, Pageable pageable);

    Long countByUserAndDateBetween(User user, LocalDate dateFrom, LocalDate dateTo);
}
