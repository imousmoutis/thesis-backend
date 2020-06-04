package gr.ioannis.thesis.service;

import com.eurodyn.qlack.fuse.aaa.repository.UserRepository;
import gr.ioannis.thesis.dto.ExpenseDTO;
import gr.ioannis.thesis.mapper.ExpenseMapper;
import gr.ioannis.thesis.model.Expense;
import gr.ioannis.thesis.repository.ExpenseCategoryRepository;
import gr.ioannis.thesis.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExpenseService {

  private ExpenseRepository expenseRepository;

  private ExpenseCategoryRepository expenseCategoryRepository;

  private UserRepository userRepository;

  private ExpenseMapper expenseMapper;

  @Autowired
  public ExpenseService(ExpenseRepository expenseRepository,
      ExpenseCategoryRepository expenseCategoryRepository,
      UserRepository userRepository, ExpenseMapper expenseMapper) {
    this.expenseRepository = expenseRepository;
    this.expenseCategoryRepository = expenseCategoryRepository;
    this.userRepository = userRepository;
    this.expenseMapper = expenseMapper;
  }

  public String saveExpense(ExpenseDTO expenseDTO, String username) {
    Expense expense = expenseMapper.mapToEntity(expenseDTO);
    expense.setExpenseCategory(expenseCategoryRepository.findById(expenseDTO.getCategory()).get());
    expense.setUser(userRepository.findByUsername(username));

    expense = expenseRepository.save(expense);

    return expense.getId();
  }

  public List<ExpenseDTO> getUserExpenses(Date from, Date to, String username) {
    List<Expense> expenses = expenseRepository.findAllByUserAndDateBetween(userRepository.findByUsername(username),
        from, to);
    return expenseMapper.mapToDTO(expenses);
  }
}
