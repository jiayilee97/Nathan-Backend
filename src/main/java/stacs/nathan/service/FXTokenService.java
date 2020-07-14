package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.FXTokenDataEntryRequestDto;
import stacs.nathan.dto.request.FXTokenRequestDto;
import stacs.nathan.dto.response.ClientOpenPositionResponseDto;
import stacs.nathan.dto.response.FXTokenDataEntryResponseDto;
import stacs.nathan.dto.response.FXTokenResponseDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.FXToken;
import stacs.nathan.entity.User;
import java.util.List;

public interface FXTokenService {

    List<SPTokenResponseDto> fetchAvailableTokens(User user);

    void createFXToken(FXTokenRequestDto token) throws ServerErrorException;

    void closeFXToken(String tokenCode) throws ServerErrorException;

    List<ClientOpenPositionResponseDto> fetchClientOpenPosition(String issuerId);

    List<FXTokenResponseDto> fetchAllFxTokens(User user);

    FXTokenResponseDto fetchTokenById(String tokenCode);

    void enterSpotPrice(FXTokenDataEntryRequestDto dto) throws ServerErrorException;

    List<FXTokenResponseDto> fetchAvailableFXTokens();

    List<FXTokenDataEntryResponseDto> fetchDataEntryHistory();

    void executeUnconfirmedChain();

    void executeUnavailableChain();

    String fetchAppWalletAddress();

    FXToken fetchByTokenCode(String tokenCode);

}
