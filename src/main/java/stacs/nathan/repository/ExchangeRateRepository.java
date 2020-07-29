package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.ExchangeRate;


@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
}
