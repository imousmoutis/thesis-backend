package gr.ioannis.thesis.mapper;

import gr.ioannis.thesis.dto.ExpenseDTO;
import gr.ioannis.thesis.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenseMapper {

  Expense mapToEntity(ExpenseDTO expenseDTO);

  @Mapping(source = "expenseCategory.id", target = "category")
  ExpenseDTO mapToDTO(Expense expense);

  List<ExpenseDTO> mapToDTO(List<Expense> expenses);

}
