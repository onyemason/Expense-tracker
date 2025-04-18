package com.codeElevate.ExpenseTracker.services.expense;

import com.codeElevate.ExpenseTracker.dto.ExpenseDTO;
import com.codeElevate.ExpenseTracker.entity.Expense;
import com.codeElevate.ExpenseTracker.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Expense postExpense(ExpenseDTO expenseDTO){
        return  saveorUpdateExpense(new Expense(), expenseDTO);
    }

    private Expense saveorUpdateExpense(Expense expense, ExpenseDTO expenseDTO){
        expense.setTitle(expenseDTO.getTitle());
        expense.setDate(expenseDTO.getDate());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDescription(expenseDTO.getDescription());

        return  expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, ExpenseDTO expenseDTO){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
            return saveorUpdateExpense(optionalExpense.get(), expenseDTO);
        }else{
            throw new EntityNotFoundException("Expense is not present with id " + id);
        }
    }

    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll().stream().sorted(Comparator.comparing(Expense::getDate).reversed())
                .collect(Collectors.toList());
    }
    public  Expense getExpenseById(Long id){
        Optional<Expense> optionalExpense =expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
            return optionalExpense.get();
        }else{
            throw new EntityNotFoundException("Expense is not present with id"+ " " + id);
        }
    }

    public void deleteExpense(Long id){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
        expenseRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Expense is not present with id" + id);
        }
    }
}
