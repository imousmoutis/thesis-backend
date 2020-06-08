package gr.ioannis.thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageableExpenseDTO {

  private long size;

  private List<ExpenseDTO> results;

}
