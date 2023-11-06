package com.edstem.expensemanager.repository;

import com.edstem.expensemanager.model.Transaction;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByDate(LocalDate date);
}
