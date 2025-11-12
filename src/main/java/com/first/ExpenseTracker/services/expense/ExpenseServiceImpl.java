package com.first.ExpenseTracker.services.expense;

import com.first.ExpenseTracker.dto.ExpenseDTO;
import com.first.ExpenseTracker.entity.Expense;
import com.first.ExpenseTracker.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    private final ExpenseRepository expenseRepository;

    public Expense postExpense(ExpenseDTO expenseDTO) {
        return saveOrUpdateExpense(new Expense(), expenseDTO);
    }

    private Expense saveOrUpdateExpense(Expense expense, ExpenseDTO expenseDTO){
        expense.setTitle(expenseDTO.getTitle());
        expense.setDate(expenseDTO.getDate());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDescription(expenseDTO.getDescription());

        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll().stream().sorted(Comparator.comparing(Expense::getDate).
                reversed()).collect(Collectors.toList());
    }

    public Expense getExpenseById(Long id){
        Optional<Expense> OptionalExpense = expenseRepository.findById(id);
        if(OptionalExpense.isPresent()){
            return OptionalExpense.get();
        }else {
            throw new EntityNotFoundException("Expense with id " + id + " not found");
        }
    }

    public Expense updateExpense(Long id, ExpenseDTO expenseDTO){
        Optional<Expense> OptionalExpense = expenseRepository.findById(id);
        if(OptionalExpense.isPresent()){
            return saveOrUpdateExpense(OptionalExpense.get(), expenseDTO);
        }
        else {
            throw new EntityNotFoundException("Expense with id " + id + " not found");
        }
    }

    public  void deleteExpense(Long id){
        Optional<Expense> OptionalExpense = expenseRepository.findById(id);
        if(OptionalExpense.isPresent()){
            expenseRepository.deleteById(id);
        }
        else{
            throw new EntityNotFoundException("Expense with id " + id + " not found");
        }
    }

}
