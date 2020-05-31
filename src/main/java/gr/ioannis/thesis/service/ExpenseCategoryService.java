package gr.ioannis.thesis.service;

import gr.ioannis.thesis.model.ExpenseCategory;
import gr.ioannis.thesis.repository.ExpenseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseCategoryService {

  private ExpenseCategoryRepository expenseCategoryRepository;

  @Autowired
  public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository) {
    this.expenseCategoryRepository = expenseCategoryRepository;
  }

  public List<ExpenseCategory> getExpenseCategories() {
    return expenseCategoryRepository.findAll();
  }
}
