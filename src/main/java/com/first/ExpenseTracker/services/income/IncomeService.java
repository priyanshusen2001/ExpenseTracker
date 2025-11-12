package com.first.ExpenseTracker.services.income;

import com.first.ExpenseTracker.dto.IncomeDTO;
import com.first.ExpenseTracker.entity.Income;
import java.util.List;

public interface IncomeService {
    Income postIncome(IncomeDTO incomeDTO);
    List <Income> getAllIncomes();
    Income getIncomeById(Long id);
    Income updateIncomes(Long id, IncomeDTO incomeDTO);
    void deleteIncome(Long id);


}
