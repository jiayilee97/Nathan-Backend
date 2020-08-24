package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.ExchangeRate;
import java.util.Date;
import java.util.List;


@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query("SELECT DISTINCT er.currencyPair from ExchangeRate er")
    List<String> findAllUniqueCurrency();

    @Query("SELECT er FROM ExchangeRate er WHERE er.updatedDate >= :startDate and er.updatedDate <= :endDate and er.isVisible = true")
    List<ExchangeRate> fetchCurrentExchangeRates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select e from ExchangeRate e where e.currencyPair = :currencyPair and e.updatedDate in (select max(updatedDate) from ExchangeRate r where e.currency = r.currency)")
    ExchangeRate findByCurrencyPair(@Param("currencyPair") String currencyPair);

    @Query("select DISTINCT e from ExchangeRate e where e.updatedDate in (select max(updatedDate) from ExchangeRate r where e.currency = r.currency)")
    List<ExchangeRate> fetchUpdatedExchangeRates();


}
