package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.Balance;
import stacs.nathan.entity.User;
import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

  List<Balance> findByUser(User user);

  @Query("SELECT b FROM Balance b where b.user.clientId = :clientId")
  List<Balance> findByClientId(@Param("clientId") String clientId);
}
