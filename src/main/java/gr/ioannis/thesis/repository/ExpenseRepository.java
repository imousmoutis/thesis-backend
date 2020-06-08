package gr.ioannis.thesis.repository;

import com.eurodyn.qlack.fuse.aaa.model.User;
import gr.ioannis.thesis.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {

  List<Expense> findAllByUserAndDateBetweenOrderByDateAsc(User user, Date from, Date to);

  Page<Expense> findAllByUserOrderByDateAsc(User user, Pageable pageable);

}
