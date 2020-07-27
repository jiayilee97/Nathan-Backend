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

    List<FXToken> findByStatus(FXTokenStatus status);

    @Query("SELECT fx FROM FXToken fx WHERE fx.tokenCode =?1")
    FXToken findByTokenCode(@Param("tokenCode") String tokenCode);

    @Query("SELECT NEW stacs.nathan.dto.response.ClientOpenPositionResponseDto(fx.tokenCode, fx.spToken.tokenCode, fx.spToken.contractInceptionDate, fx.spToken.maturityDate) " +
        "FROM FXToken fx WHERE fx.spToken.clientId = :clientId and fx.spToken.status = 'ACTIVE'")
    List<ClientOpenPositionResponseDto> fetchClientOpenPosition(@Param("clientId") String clientId);

    @Query("SELECT NEW stacs.nathan.dto.response.FXTokenResponseDto(fx.tokenCode, fx.spToken.tokenCode, fx.spToken.contractInceptionDate, fx.spToken.maturityDate, fx.amount, fx.status, fx.issuerId, u.displayName, fx.spToken.clientId)" +
            "FROM FXToken fx join User u on fx.spToken.clientId = u.clientId")
    List<FXTokenResponseDto> fetchAllFxTokens();

    @Query("SELECT NEW stacs.nathan.dto.response.FXTokenResponseDto(fx.tokenCode, fx.spToken.tokenCode, fx.spToken.contractInceptionDate, fx.spToken.maturityDate, fx.amount, fx.status, fx.issuerId, fx.spToken.clientId, fx.fxCurrency, fx.currencyCode, fx.issuerAddress)" +
            "FROM FXToken fx WHERE fx.tokenCode =?1")
    FXTokenResponseDto fetchTokenById(String tokenCode);

    @Query("SELECT NEW stacs.nathan.dto.response.FXTokenResponseDto(fx.tokenCode, fx.spToken.tokenCode, fx.spToken.contractInceptionDate, fx.spToken.maturityDate, fx.amount, fx.status, fx.issuerId, fx.spToken.clientId, fx.fxCurrency, fx.currencyCode, fx.issuerAddress)" +
            "FROM FXToken fx WHERE fx.status = 'OPEN'")
   List<FXTokenResponseDto> fetchAvailableFXTokens();

    @Query("SELECT NEW stacs.nathan.dto.response.FXTokenResponseDto(fx.tokenCode, fx.spToken.tokenCode, fx.spToken.contractInceptionDate, fx.spToken.maturityDate, fx.amount, fx.status, fx.issuerId, fx.spToken.clientId, fx.fxCurrency, fx.currencyCode, fx.issuerAddress)" +
            "FROM FXToken fx WHERE fx.status = 'KNOCK_OUT' OR fx.status = 'MATURED'")
    List<FXTokenResponseDto> fetchAllMaturedOrKnockout();
}
