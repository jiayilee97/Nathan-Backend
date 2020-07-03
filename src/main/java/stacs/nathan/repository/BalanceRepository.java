package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.Balance;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

}
