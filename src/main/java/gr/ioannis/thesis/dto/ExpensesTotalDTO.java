package gr.ioannis.thesis.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ExpensesTotalDTO {

  private List<String> dates;

  private BigDecimal[][] totalExpenses;

}
