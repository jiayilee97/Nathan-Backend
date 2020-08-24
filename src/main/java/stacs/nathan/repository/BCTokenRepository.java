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

  @Query("SELECT NEW stacs.nathan.dto.response.BCTokenResponseDto(bc.id, bc.underlyingCurrency, bc.tokenCode, bc.currencyCode, bc.amount) " +
      "FROM BaseCurrencyToken bc WHERE bc.issuerAddress = :issuerAddress AND bc.status = :status and bc.isVisible = true")
  List<BCTokenResponseDto> fetchAllByIssuerAddress(@Param("issuerAddress") String issuerAddress, @Param("status")BCTokenStatus status);

  @Query("SELECT NEW stacs.nathan.dto.response.BCTokenResponseDto(bc.id, bc.underlyingCurrency, bc.tokenCode, bc.currencyCode, bc.amount) " +
      "FROM BaseCurrencyToken bc WHERE bc.tokenCode = :tokenCode and bc.isVisible = true")
  BCTokenResponseDto fetchByTokenCode(@Param("tokenCode") String tokenCode);

  List<BaseCurrencyToken> findByStatus(BCTokenStatus status);

  BaseCurrencyToken findByTokenCodeAndIsVisible(String tokenCode, Boolean isVisible);

}
