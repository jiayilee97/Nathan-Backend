package stacs.nathan.service;

import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.FXTokenDataEntryRequestDto;
import stacs.nathan.dto.request.FXTokenRequestDto;
import stacs.nathan.dto.response.*;
import stacs.nathan.entity.FXToken;
import stacs.nathan.entity.User;
import java.util.List;

public interface FXTokenService {

    CreateFXTokenInitDto fetchInitForm();

    List<SPTokenResponseDto> fetchAvailableTokens(User user);

    void save(FXToken fxToken);

    void createFXToken(FXTokenRequestDto token) throws ServerErrorException, BadRequestException;

    void closeFXToken(String tokenCode) throws ServerErrorException;

    List<ClientOpenPositionResponseDto> fetchClientOpenPosition(String clientId);

    List<FXTokenResponseDto> fetchAllFxTokens(User user);

    FXTokenResponseDto fetchTokenById(String tokenCode);

    void enterSpotPrice(FXTokenDataEntryRequestDto dto) throws ServerErrorException;

    List<FXTokenResponseDto> fetchAvailableFXTokens();

    List<FXTokenDataEntryResponseDto> fetchDataEntryHistory();

    void executeUnconfirmedChain(String username);

    void executeUnavailableChain(String username);

    String fetchAppWalletAddress();

    FXToken fetchByTokenCode(String tokenCode);

    List<FXTokenResponseDto> fetchMaturedOrKnockout();

}
