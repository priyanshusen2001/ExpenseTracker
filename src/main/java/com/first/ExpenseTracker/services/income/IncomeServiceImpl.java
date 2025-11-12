package com.first.ExpenseTracker.services.income;

import com.first.ExpenseTracker.dto.IncomeDTO;
import com.first.ExpenseTracker.entity.Income;
import com.first.ExpenseTracker.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private final IncomeRepository incomeRepository;

    public Income postIncome(IncomeDTO incomeDTO) {
        return saveOrUpdateIncome(new Income(), incomeDTO);
    }

    private Income saveOrUpdateIncome(Income income, IncomeDTO incomeDTO) {

        income.setTitle(incomeDTO.getTitle());
        income.setDescription(incomeDTO.getDescription());
        income.setAmount(incomeDTO.getAmount());
        income.setCategory(incomeDTO.getCategory());
        income.setDate(incomeDTO.getDate());
        return incomeRepository.save(income);
    }

    public List <Income> getAllIncomes() {
        return incomeRepository.findAll().stream().sorted(Comparator.comparing(Income::getDate).
                reversed()).collect(Collectors.toList());
    }
    public Income getIncomeById(Long id) {
        Optional<Income> income = incomeRepository.findById(id);
        if(income.isPresent()){
            return income.get();
        }
        else{
            throw new EntityNotFoundException("Income not found for id: " + id);
        }
    }

public Income updateIncomes(Long id, IncomeDTO dto){
    Optional<Income> op = incomeRepository.findById(id);
    if(op.isPresent()){
        return saveOrUpdateIncome(op.get(), dto);
    }
    else {
        throw new EntityNotFoundException("Expense with id " + id + " not found");
    }
}

    public void deleteIncome(Long id) {
        Optional<Income> income = incomeRepository.findById(id);
        if(income.isPresent()){
            incomeRepository.delete(income.get());
        }
        else {
            throw new EntityNotFoundException("Income not found for id: " + id);
        }
    }
}
