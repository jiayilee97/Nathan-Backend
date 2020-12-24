package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.FixingDate;
import stacs.nathan.entity.SPToken;
import java.util.Date;
import java.util.List;

@Repository
public interface FixingDateRepository extends JpaRepository<FixingDate, Long> {

  @Query("SELECT fd.spToken FROM FixingDate fd " +
      "WHERE fd.fixingDate >=:startDate AND fd.fixingDate <:endDate AND " +
      "fd.spToken.status = 'ACTIVE' AND " +
      "fd.fixingDate <:endDate AND fd.isVisible = true AND fd.status = true AND " +
      "fd.spToken IS NOT NULL AND fd.spToken.underlyingCurrency =:currency")
  List<SPToken> findByFixingDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("currency") String currency);

  FixingDate findByFixingDateAndSpToken(Date fixingDate, SPToken spToken);

}
