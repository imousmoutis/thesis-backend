package gr.ioannis.thesis.controller;

import com.eurodyn.qlack.fuse.aaa.annotation.ResourceAccess;
import gr.ioannis.thesis.dto.ExpenseDTO;
import gr.ioannis.thesis.dto.ExpensesTotalDTO;
import gr.ioannis.thesis.model.ExpenseCategory;
import gr.ioannis.thesis.service.ExpenseCategoryService;
import gr.ioannis.thesis.service.ExpenseService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping(value = "/expense")
public class ExpenseController {

  private ExpenseCategoryService expenseCategoryService;

  private ExpenseService expenseService;

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  @Autowired
  public ExpenseController(ExpenseCategoryService expenseCategoryService, ExpenseService expenseService) {
    this.expenseCategoryService = expenseCategoryService;
    this.expenseService = expenseService;
  }

  @GetMapping(value = "/categories")
  @ResourceAccess(roleAccess = {"User"})
  public List<ExpenseCategory> getCategories() {
    return expenseCategoryService.getExpenseCategories();
  }

  @PostMapping
  @ResourceAccess(roleAccess = {"User"})
  public ResponseEntity<Object> saveExpense(@RequestBody ExpenseDTO expenseDTO,
      @AuthenticationPrincipal UserDetails userDetails) {
    expenseService.saveExpense(expenseDTO, userDetails.getUsername());
    return ResponseEntity.noContent().build();
  }

  @SneakyThrows
  @GetMapping("/total")
  @ResourceAccess(roleAccess = {"User"})
  public ExpensesTotalDTO getUserTotalExpenses(@RequestParam(name = "from") String from,
      @RequestParam(name = "to") String to,
      @AuthenticationPrincipal UserDetails userDetails) {
    return expenseService.getUserTotalExpenses(dateFormat.parse(from), dateFormat.parse(to), userDetails.getUsername());
  }

}
