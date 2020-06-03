package gr.ioannis.thesis.mapper;

import gr.ioannis.thesis.dto.ExpenseDTO;
import gr.ioannis.thesis.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenseMapper {

  Expense mapToEntity(ExpenseDTO expenseDTO);

}
