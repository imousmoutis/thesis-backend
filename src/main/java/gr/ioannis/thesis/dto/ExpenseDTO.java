package gr.ioannis.thesis.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ExpenseDTO {

  private BigDecimal amount;

  private Date date;

  private String description;

  private String category;

}
