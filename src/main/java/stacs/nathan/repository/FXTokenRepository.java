package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.dto.response.ClientOpenPositionResponseDto;
import stacs.nathan.dto.response.FXTokenResponseDto;
import stacs.nathan.entity.FXToken;
import stacs.nathan.utils.enums.FXTokenStatus;
import java.util.List;

@Repository
public interface FXTokenRepository extends JpaRepository<FXToken, Long> {

    @Query("SELECT fx FROM FXToken fx WHERE fx.status = :status")
    List<FXToken> fetchAllUnconfirmedChain(@Param("status") FXTokenStatus status);

    @Query("SELECT fx FROM FXToken fx WHERE fx.tokenCode =?1")
    FXToken findByTokenCode(String tokenCode);

    @Query("SELECT NEW stacs.nathan.dto.response.ClientOpenPositionResponseDto(fx.tokenCode, fx.spToken.tokenCode, fx.spToken.contractInceptionDate, fx.spToken.maturityDate) " +
        "FROM FXToken fx WHERE fx.issuerId = :issuerId and fx.spToken.status = 'ACTIVE'")
    List<ClientOpenPositionResponseDto> fetchClientOpenPosition(@Param("issuerId") String issuerId);

    @Query("SELECT NEW stacs.nathan.dto.response.FXTokenResponseDto(fx.tokenCode, fx.spToken.tokenCode, fx.spToken.contractInceptionDate, fx.spToken.maturityDate, fx.amount, fx.status, fx.issuerId, fx.spToken.clientId)" +
            "FROM FXToken fx")
    List<FXTokenResponseDto> fetchAllFxTokens();
}
