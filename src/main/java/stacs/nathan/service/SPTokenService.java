package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.SPTokenRequestDto;
import stacs.nathan.dto.response.CreateSPTokenInitDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;
import stacs.nathan.utils.enums.SPTokenStatus;
import java.util.List;

public interface SPTokenService {

  void save(SPToken token);

  void createSPToken(SPTokenRequestDto token) throws ServerErrorException;

  List<SPTokenResponseDto> fetchAllTokens();

  List<SPToken> fetchTokensByStatus(SPTokenStatus status);

  List<SPTokenResponseDto> fetchAllOpenPositions(User user);

  List<SPTokenResponseDto> fetchAllClosedPositions(User user);

  // for risk module calculation
  List<SPToken> fetchAllOpenPositionsForRisk(String clientId, List<String> currencyPairs);

  List<SPTokenResponseDto> fetchAllOpenPositionsByClientId(String clientId);

  List<SPTokenResponseDto> fetchAllClosedPositionsByClientId(String clientId);

  CreateSPTokenInitDto fetchInitForm();

  SPTokenResponseDto fetchByTokenCode(String tokenCode);

  SPToken fetchSPTokenByTokenCode(String tokenCode);

  void transferToBurnAddress(String tokenCode) throws ServerErrorException;

  void executeUnconfirmedChain();

  void executeUnavailableChain();

  void checkSPTokenMaturity() throws ServerErrorException;

  SPToken findAvailableSPTokenByTokenCode(String tokenCode);

}
