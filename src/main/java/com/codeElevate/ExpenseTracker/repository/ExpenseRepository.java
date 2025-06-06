package com.codeElevate.ExpenseTracker.repository;

import com.codeElevate.ExpenseTracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository  extends JpaRepository<Expense, Long> {
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);


    @Query("SELECT SUM(e.amount) FROM Expense  e")
    Double sumALLAmounts();

    Optional<Expense> findFirstByOrderByDateDesc();
}