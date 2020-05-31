package gr.ioannis.thesis.controller;

import com.eurodyn.qlack.fuse.aaa.annotation.ResourceAccess;
import gr.ioannis.thesis.model.ExpenseCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gr.ioannis.thesis.service.ExpenseCategoryService;

import java.util.List;

@RestController
@RequestMapping(value = "/expense")
public class ExpenseController {

  private ExpenseCategoryService expenseCategoryService;

  @Autowired
  public ExpenseController(ExpenseCategoryService expenseCategoryService) {
    this.expenseCategoryService = expenseCategoryService;
  }

  @GetMapping(value = "/categories")
  @ResourceAccess(roleAccess = {"Search User"})
  public List<ExpenseCategory> getCategories() {
    return expenseCategoryService.getExpenseCategories();
  }

}
