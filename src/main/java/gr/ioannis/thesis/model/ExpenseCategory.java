package gr.ioannis.thesis.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "expense_category")
@Getter
@Setter
public class ExpenseCategory {

  @Id
  private String id;

  private String name;

}
