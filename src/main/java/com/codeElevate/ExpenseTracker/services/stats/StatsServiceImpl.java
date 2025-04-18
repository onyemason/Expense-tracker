package com.codeElevate.ExpenseTracker.services.stats;

import com.codeElevate.ExpenseTracker.dto.GraphDTO;
import com.codeElevate.ExpenseTracker.dto.StatsDTO;
import com.codeElevate.ExpenseTracker.entity.Expense;
import com.codeElevate.ExpenseTracker.entity.Income;
import com.codeElevate.ExpenseTracker.repository.ExpenseRepository;
import com.codeElevate.ExpenseTracker.repository.IncomeRepository;
import com.sun.jdi.DoubleValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor

public class StatsServiceImpl implements  StatsService{
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public GraphDTO getChartData(){
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(27);

        GraphDTO graphDTO = new GraphDTO();
        graphDTO.setExpenseList(expenseRepository.findByDateBetween(startDate, endDate));
        graphDTO.setIncomeList(incomeRepository.findByDateBetween(startDate,endDate));
        return graphDTO;
    }

    public StatsDTO getStats(){
       Double totalIncome = incomeRepository.sumALLAmounts();
        Double totalExpense = expenseRepository.sumALLAmounts();

        Optional<Income> optionalIncome = incomeRepository.findFirstByOrderByDateDesc();
        Optional<Expense> optionalExpense = expenseRepository.findFirstByOrderByDateDesc();

        StatsDTO statsDTO= new StatsDTO();
        statsDTO.setExpense(totalExpense);
        statsDTO.setIncome(totalIncome);

       /*
       *if(optionalIncome.ispresent{
       * statsDTO.setLatestIncome(optionalIncome.get()}) */
        optionalIncome.ifPresent(statsDTO::setLatestIncome);
        optionalExpense.ifPresent(statsDTO::setLatestExpense);

        statsDTO.setBalance(totalIncome - totalExpense);
        List<Income> incomeList = incomeRepository.findAll();
        List<Expense> expenseList = expenseRepository.findAll();
        // logic to get the minimum  & maximum income
        OptionalDouble  minIncome = incomeList.stream().mapToDouble(Income::getAmount).min();
        OptionalDouble  maxIncome = incomeList.stream().mapToDouble(Income::getAmount).max();


        OptionalDouble  minExpense = expenseList.stream().mapToDouble(Expense::getAmount).min();
        OptionalDouble  maxExpense = expenseList.stream().mapToDouble(Expense::getAmount).max();

        statsDTO.setMaxExpense(maxExpense.isPresent()? maxExpense.getAsDouble():null);
        statsDTO.setMinExpense(minExpense.isPresent()? minExpense.getAsDouble():null);

        statsDTO.setMaxIncome(maxIncome.isPresent()? maxIncome.getAsDouble():null);
        statsDTO.setMinIncome(minIncome.isPresent()? minIncome.getAsDouble():null);

        return statsDTO;
    }
}
