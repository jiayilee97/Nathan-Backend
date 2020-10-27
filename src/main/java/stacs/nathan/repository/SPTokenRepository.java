package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;
import stacs.nathan.utils.enums.SPTokenStatus;
import java.util.List;

@Repository
public interface SPTokenRepository extends JpaRepository<SPToken, Long> {

  List<SPToken> findByStatus(SPTokenStatus status);

  @Query("SELECT sp FROM SPToken sp WHERE sp.status = :status AND sp.fxToken IS NULL ")
  List<SPToken> fetchAvailableTokens(@Param("status") SPTokenStatus status);

  @Query("SELECT NEW stacs.nathan.dto.response.SPTokenResponseDto(sp.tokenCode, sp.productType, sp.contractInceptionDate, sp.underlyingCurrency, sp.notionalAmount, sp.fixingAmount, sp.spotPrice, sp.strikeRate, sp.knockOutPrice, sp.maturityDate, sp.fixingPage, sp.numberOfFixing, sp.cpId, sp.opsId, sp.issuingAddress, sp.status, sp.user.displayName, sp.clientId)" +
          "FROM SPToken sp WHERE sp.user = :user AND sp.status = :status AND sp.isVisible = true")
  List<SPTokenResponseDto> fetchAllOpenPositions(@Param("user") User user, @Param("status") SPTokenStatus status);

  @Query("SELECT sp FROM SPToken sp WHERE sp.clientId = :clientId AND sp.status = :status AND sp.isVisible = true and sp.underlyingCurrency in :currencyPairs")
  List<SPToken> fetchAllOpenPositionsForRisk(@Param("clientId") String clientId, @Param("status") SPTokenStatus status, @Param("currencyPairs") List<String> currencyPairs);

  @Query("SELECT NEW stacs.nathan.dto.response.SPTokenResponseDto(sp.tokenCode, sp.productType, sp.contractInceptionDate, sp.underlyingCurrency, sp.notionalAmount, sp.fixingAmount, sp.spotPrice, sp.strikeRate, sp.knockOutPrice, sp.maturityDate, sp.fixingPage, sp.numberOfFixing, sp.cpId, sp.opsId, sp.issuingAddress, sp.status, sp.user.displayName, sp.clientId)" +
      "FROM SPToken sp WHERE sp.user = :user AND sp.status in :status AND sp.isVisible = true")
  List<SPTokenResponseDto> fetchAllClosedPositions(@Param("user") User user, @Param("status") List<SPTokenStatus> status);

  @Query("SELECT NEW stacs.nathan.dto.response.SPTokenResponseDto(sp.tokenCode, sp.productType, sp.contractInceptionDate, sp.underlyingCurrency, sp.notionalAmount, sp.fixingAmount, sp.spotPrice, sp.strikeRate, sp.knockOutPrice, sp.maturityDate, sp.fixingPage, sp.numberOfFixing, sp.cpId, sp.opsId, sp.issuingAddress, sp.status, sp.user.displayName, sp.clientId)" +
      "FROM SPToken sp WHERE sp.clientId = :clientId AND sp.status = :status AND sp.isVisible = true")
  List<SPTokenResponseDto> fetchAllOpenPositionsByClientId(@Param("clientId") String clientId, @Param("status") SPTokenStatus status);

  @Query("SELECT NEW stacs.nathan.dto.response.SPTokenResponseDto(sp.tokenCode, sp.productType, sp.contractInceptionDate, sp.underlyingCurrency, sp.notionalAmount, sp.fixingAmount, sp.spotPrice, sp.strikeRate, sp.knockOutPrice, sp.maturityDate, sp.fixingPage, sp.numberOfFixing, sp.cpId, sp.opsId, sp.issuingAddress, sp.status, sp.user.displayName, sp.clientId)" +
      "FROM SPToken sp WHERE sp.clientId = :clientId AND sp.status in :status AND sp.isVisible = true")
  List<SPTokenResponseDto> fetchAllClosedPositionsByClientId(@Param("clientId") String clientId, @Param("status") List<SPTokenStatus> status);

  @Query("SELECT NEW stacs.nathan.dto.response.SPTokenResponseDto(sp.tokenCode, sp.productType, sp.contractInceptionDate, sp.underlyingCurrency, sp.notionalAmount, sp.fixingAmount, sp.spotPrice, sp.strikeRate, sp.knockOutPrice, sp.maturityDate, sp.fixingPage, sp.numberOfFixing, sp.cpId, sp.opsId, sp.issuingAddress, sp.status, sp.user.displayName, sp.clientId, sp.fixingType, sp.tenor, sp.tenorType)" +
      "FROM SPToken sp WHERE sp.tokenCode =?1 AND sp.isVisible = true")
  SPTokenResponseDto findByTokenCode(String tokenCode);

  @Query("SELECT sp FROM SPToken sp WHERE sp.tokenCode =?1 AND sp.isVisible = true")
  SPToken findSPTokenByTokenCode(String tokenCode);

  @Query("SELECT NEW stacs.nathan.dto.response.SPTokenResponseDto(sp.tokenCode, sp.productType, sp.contractInceptionDate, sp.underlyingCurrency, sp.notionalAmount, sp.fixingAmount, sp.spotPrice, sp.strikeRate, sp.knockOutPrice, sp.maturityDate, sp.fixingPage, sp.numberOfFixing, sp.cpId, sp.opsId, sp.issuingAddress, sp.status, sp.user.displayName, u.displayName, sp.clientId)" +
          "FROM SPToken sp join User u on sp.clientId = u.clientId AND sp.isVisible = true")
  List<SPTokenResponseDto> fetchAllTokens();

  // Used by FXTokenService
  @Query("SELECT sp FROM SPToken sp WHERE sp.user = :user and sp.status = :status AND sp.isVisible = true")
  List<SPToken> fetchAvailableTokens(@Param("user") User user, @Param("status") SPTokenStatus status);

  @Query("SELECT sp FROM SPToken sp WHERE sp.tokenCode =?1 AND sp.fxToken = null AND sp.isVisible = true")
  SPToken findAvailableSPTokenByTokenCode(String tokenCode);

//  @Modifying
//  @Transactional
//  @Query("UPDATE SPToken sp set sp.availability = 0 WHERE sp.tokenCode =?1")
//  void updateSPTokenAvailabilityByTokenCode(String tokenCode);
}
