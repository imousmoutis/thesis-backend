package gr.ioannis.thesis.repository;

import com.eurodyn.qlack.fuse.aaa.model.User;
import gr.ioannis.thesis.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {

  List<Expense> findAllByUserAndDateBetween(User user, Date from, Date to);

}
