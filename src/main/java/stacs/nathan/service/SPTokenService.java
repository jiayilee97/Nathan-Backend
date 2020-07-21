package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.SPTokenRequestDto;
import stacs.nathan.dto.response.CreateSPTokenInitDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;
import java.util.List;

public interface SPTokenService {

  void createSPToken(SPTokenRequestDto token) throws ServerErrorException;

  List<SPTokenResponseDto> fetchAllTokens();

  List<SPTokenResponseDto> fetchAllOpenPositions(User user);

  List<SPTokenResponseDto> fetchAllClosedPositions(User user);

  List<SPTokenResponseDto> fetchAllOpenPositionsByClientId(String clientId);

  List<SPTokenResponseDto> fetchAllClosedPositionsByClientId(String clientId);

  CreateSPTokenInitDto fetchInitForm();

  SPTokenResponseDto fetchByTokenCode(String tokenCode);

  SPToken fetchSPTokenByTokenCode(String tokenCode);

  void transferToBurnAddress(String tokenCode) throws ServerErrorException;

  void executeUnconfirmedChain(String username);

  void executeUnavailableChain(String username);

  void checkSPTokenMaturity(String username) throws ServerErrorException;

}
