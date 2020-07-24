package gr.ioannis.thesis.service;

import com.eurodyn.qlack.fuse.aaa.repository.UserRepository;
import gr.ioannis.thesis.dto.ExpenseDTO;
import gr.ioannis.thesis.dto.ExpensesTotalDTO;
import gr.ioannis.thesis.dto.PageableExpenseDTO;
import gr.ioannis.thesis.mapper.ExpenseMapper;
import gr.ioannis.thesis.model.Expense;
import gr.ioannis.thesis.model.ExpenseCategory;
import gr.ioannis.thesis.repository.ExpenseCategoryRepository;
import gr.ioannis.thesis.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

  private ExpenseRepository expenseRepository;

  private ExpenseCategoryRepository expenseCategoryRepository;

  private UserRepository userRepository;

  private ExpenseMapper expenseMapper;

  private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

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

  public void deleteExpense(String expenseId) {
    expenseRepository.deleteById(expenseId);
  }

  public ExpensesTotalDTO getUserTotalExpenses(Date from, Date to, String username) {

    ExpensesTotalDTO expensesTotalDTO = new ExpensesTotalDTO();
    List<String> dates = new ArrayList<>();

    List<Expense> expenses = expenseRepository
        .findAllByUserAndDateBetweenOrderByDateAsc(userRepository.findByUsername(username),
            from, to);

    SortedMap<Date, Map<ExpenseCategory, List<Expense>>> filteredExpenses =
        expenses.stream().collect(Collectors.groupingBy(Expense::getDate, TreeMap::new,
            Collectors.groupingBy(Expense::getExpenseCategory)));

    int datesSum = filteredExpenses.size();
    BigDecimal[][] expensesArray = new BigDecimal[19][datesSum];
    for (BigDecimal[] row : expensesArray) {
      Arrays.fill(row, BigDecimal.ZERO);
    }

    int dateIndex = 0;
    for (Entry<Date, Map<ExpenseCategory, List<Expense>>> date : filteredExpenses.entrySet()) {
      Date dateKey = date.getKey();
      dates.add(DATE_FORMATTER.format(dateKey));

      for (Entry<ExpenseCategory, List<Expense>> expenseCategory : filteredExpenses.get(dateKey).entrySet()) {
        ExpenseCategory expenseCategoryKey = expenseCategory.getKey();

        for (Expense expense : expenseCategory.getValue()) {
          expensesArray[expenseCategoryKey.getId() - 1][dateIndex] =
              expensesArray[expenseCategoryKey.getId() - 1][dateIndex].add(expense.getAmount());
        }
      }

      dateIndex++;
    }

    expensesTotalDTO.setDates(dates);
    expensesTotalDTO.setTotalExpenses(expensesArray);

    return expensesTotalDTO;
  }

  public PageableExpenseDTO getUserExpenses(int page, int size, String username) {
    Page<Expense> results = expenseRepository.findAllByUserOrderByDateAsc(userRepository.findByUsername(username),
        PageRequest.of(page, size));

    return new PageableExpenseDTO(results.getTotalElements(),
        results.stream().map(expenseMapper::mapToDTO).collect(Collectors.toList()));
  }
}
