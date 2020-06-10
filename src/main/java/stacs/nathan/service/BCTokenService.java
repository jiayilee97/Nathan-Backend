package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;
import stacs.nathan.dto.response.BCTokenResponseDto;

import java.util.List;

public interface BCTokenService {

  void createBCToken(BCTokenRequestDto token) throws ServerErrorException;

  List<BCTokenResponseDto> fetchAllByIssuerAddress(String issuerAddress) throws ServerErrorException;

}
