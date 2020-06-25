package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.FXTokenRequestDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.User;

import java.util.List;

public interface FXTokenService {

    List<SPTokenResponseDto> fetchAvailableTokens(User user);

    void createFXToken(FXTokenRequestDto token) throws ServerErrorException;

    void closeFXToken(String tokenCode) throws ServerErrorException;

}
