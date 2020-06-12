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

  @Query("SELECT NEW stacs.nathan.dto.response.SPTokenResponseDto(sp.tokenCode, sp.productType, sp.contractInceptionDate, sp.underlyingCurrency, sp.notionalAmount, sp.fixingAmount, sp.spotPrice, sp.strikeRate, sp.knockOutPrice, sp.maturityDate, sp.fixingPage, sp.numberOfFixing, sp.cpId, sp.opsId, sp.issuingAddress, sp.status, sp.user.displayName, sp.user.clientId)" +
          "FROM SPToken sp WHERE sp.user = :user AND sp.status = :status")
  List<SPTokenResponseDto> fetchAllOpenPositions(@Param("user") User user, @Param("status") SPTokenStatus status);

  @Query("SELECT sp FROM SPToken sp WHERE sp.status = :status")
  List<SPToken> fetchAllUnconfirmedChain(@Param("status") SPTokenStatus status);

  @Query("SELECT sp FROM SPToken sp WHERE sp.user = :user AND sp.status != :status")
  List<SPTokenResponseDto> fetchAllClosedPositions(@Param("user") User user, @Param("status") SPTokenStatus status);

  @Query("SELECT NEW stacs.nathan.dto.response.SPTokenResponseDto(sp.tokenCode, sp.productType, sp.contractInceptionDate, sp.underlyingCurrency, sp.notionalAmount, sp.fixingAmount, sp.spotPrice, sp.strikeRate, sp.knockOutPrice, sp.maturityDate, sp.fixingPage, sp.numberOfFixing, sp.cpId, sp.opsId, sp.issuingAddress, sp.status, sp.user.displayName, sp.user.clientId)" +
          "FROM SPToken sp WHERE id =?1")
  SPTokenResponseDto findSPTokenById(Long id);

  @Query("SELECT NEW stacs.nathan.dto.response.SPTokenResponseDto(sp.tokenCode, sp.productType, sp.contractInceptionDate, sp.underlyingCurrency, sp.notionalAmount, sp.fixingAmount, sp.spotPrice, sp.strikeRate, sp.knockOutPrice, sp.maturityDate, sp.fixingPage, sp.numberOfFixing, sp.cpId, sp.opsId, sp.issuingAddress, sp.status, sp.user.displayName, sp.user.clientId)" +
          "FROM SPToken sp WHERE sp.user = :user")
  List<SPTokenResponseDto> fetchAllTokens(@Param("user") User user);


}
