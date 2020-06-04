package gr.ioannis.thesis.model;

import com.eurodyn.qlack.common.model.QlackBaseModel;
import com.eurodyn.qlack.fuse.aaa.model.User;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "expense")
@Getter
@Setter
public class Expense extends QlackBaseModel {

  private BigDecimal amount;

  @Temporal(TemporalType.DATE)
  private Date date;

  private String description;

  @ManyToOne
  @JoinColumn(name="expense_category_id", nullable=false)
  private ExpenseCategory expenseCategory;

  @ManyToOne
  @JoinColumn(name="user_id", nullable=false)
  private User user;

}
