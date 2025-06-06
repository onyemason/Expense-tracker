package com.codeElevate.ExpenseTracker.repository;

import com.codeElevate.ExpenseTracker.entity.Expense;
import com.codeElevate.ExpenseTracker.entity.Income;
import com.sun.jdi.DoubleValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {
        List<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);


        @Query("SELECT SUM(i.amount) FROM Income  i")
        Double sumALLAmounts();

    Optional<Income> findFirstByOrderByDateDesc();
}
