package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.dto.response.BCTokenResponseDto;
import stacs.nathan.entity.BaseCurrencyToken;
import stacs.nathan.utils.enums.BCTokenStatus;
import java.util.List;

@Repository
public interface BCTokenRepository extends JpaRepository<BaseCurrencyToken, Long> {

  @Query("SELECT NEW stacs.nathan.dto.response.BCTokenResponseDto(bc.underlyingCurrency, bc.tokenCode, bc.currencyCode, bc.amount) FROM BaseCurrencyToken bc where bc.issuerAddress = :issuerAddress")
  List<BCTokenResponseDto> fetchAllByIssuerAddress(@Param("issuerAddress") String issuerAddress);

  @Query("SELECT bc FROM BaseCurrencyToken bc WHERE bc.status = :status")
  List<BaseCurrencyToken> fetchAllUnconfirmedChain(@Param("status")BCTokenStatus status);

}
