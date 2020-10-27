package stacs.nathan.service;

import stacs.nathan.core.audit.action.AudibleActionImplementation;
import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.FXTokenDataEntryRequestDto;
import stacs.nathan.dto.request.FXTokenRequestDto;
import stacs.nathan.dto.response.*;
import stacs.nathan.entity.FXToken;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;
import java.util.List;

public interface FXTokenService {

    CreateFXTokenInitDto fetchInitForm();

    List<SPTokenResponseDto> fetchAvailableTokens(User user);

    void save(FXToken fxToken);

    AudibleActionImplementation<FXToken> createFXToken(FXTokenRequestDto token) throws ServerErrorException, BadRequestException;

    AudibleActionImplementation<FXToken> closeFXToken(String tokenCode) throws ServerErrorException;

    List<ClientOpenPositionResponseDto> fetchClientOpenPosition(String clientId);

    List<FXTokenResponseDto> fetchAllFxTokens(User user);

    FXTokenResponseDto fetchTokenById(String tokenCode);

    List<String> processTradeTransfer(SPToken spToken, FXTokenDataEntryRequestDto dto, User ops, List<String> result) throws ServerErrorException;

    FxSpotPriceInitDto initSpotPriceForm();

    List<FXTokenDataEntryResponseDto> fetchDataEntryHistory();

    void executeUnconfirmedChain();

    void executeUnavailableChain();

    FXToken fetchByTokenCode(String tokenCode);

    List<FXTokenResponseDto> fetchMaturedOrKnockout();

    List<FXTokenResponseDto> fetchAvailableFXTokens();
}
