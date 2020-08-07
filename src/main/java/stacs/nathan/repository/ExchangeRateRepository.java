package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.ExchangeRate;
import java.util.List;


@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query("SELECT DISTINCT er.currencyPair from ExchangeRate er")
    List<String> findAllUniqueCurrency();

    List<ExchangeRate> findAll();

    ExchangeRate findByCurrencyPair(String currencyPair);


}
