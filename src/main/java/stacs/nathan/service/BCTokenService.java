package stacs.nathan.service;

import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;
import stacs.nathan.dto.request.TransferBCTokenRequestDto;
import stacs.nathan.dto.response.BCTokenResponseDto;
import stacs.nathan.dto.response.CreateBCTokenInitDto;
import java.util.List;

public interface BCTokenService {

  CreateBCTokenInitDto fetchInitForm();

  void createBCToken(BCTokenRequestDto token) throws ServerErrorException, BadRequestException;

  List<BCTokenResponseDto> fetchAllByIssuerAddress(String issuerAddress) throws ServerErrorException;

  BCTokenResponseDto fetchTokenByTokenCode(String tokenCode) throws ServerErrorException;

  void executeUnconfirmedChain(String username);

  void executeUnavailableChain(String username);

  void transferBCToken(TransferBCTokenRequestDto dto) throws ServerErrorException;

  void tradeBCTokenWithFXToken(TransferBCTokenRequestDto dto) throws ServerErrorException;

}
