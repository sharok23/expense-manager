package com.edstem.expensemanager.repository;

import com.edstem.expensemanager.model.Transaction;
import com.edstem.expensemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);

    List<Transaction> findAllByUserAndDate(User user, LocalDate date);
}
