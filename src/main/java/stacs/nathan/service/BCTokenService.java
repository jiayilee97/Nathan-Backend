package stacs.nathan.service;

import stacs.nathan.core.audit.action.AudibleActionImplementation;
import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;
import stacs.nathan.dto.request.TransferBCTokenRequestDto;
import stacs.nathan.dto.request.TransferBCTokenToOpsRequestDto;
import stacs.nathan.dto.response.BCTokenResponseDto;
import stacs.nathan.dto.response.CreateBCTokenInitDto;
import stacs.nathan.entity.BaseCurrencyToken;

import java.util.List;

public interface BCTokenService {

  CreateBCTokenInitDto fetchInitForm();

  AudibleActionImplementation<BaseCurrencyToken> createBCToken(BCTokenRequestDto token) throws ServerErrorException, BadRequestException;

  List<BCTokenResponseDto> fetchAllByIssuerAddress(String issuerAddress) throws ServerErrorException;

  BCTokenResponseDto fetchTokenByTokenCode(String tokenCode) throws ServerErrorException;

  void executeUnconfirmedChain();

  void executeUnavailableChain();

  void transferBCToken(TransferBCTokenRequestDto dto) throws ServerErrorException;

  void tradeBCTokenWithFXToken(TransferBCTokenRequestDto dto) throws ServerErrorException;

  void transferBCTokenToOpsWallet(TransferBCTokenToOpsRequestDto dto) throws ServerErrorException;

}
