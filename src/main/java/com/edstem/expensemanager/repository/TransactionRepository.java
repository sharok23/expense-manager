package com.edstem.expensemanager.repository;

import com.edstem.expensemanager.model.Transaction;
import com.edstem.expensemanager.model.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);

    List<Transaction> findAllByUserAndDate(User user, LocalDate date);
}
