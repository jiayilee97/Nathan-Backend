package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;

public interface BCTokenService {

  void createBCToken(BCTokenRequestDto token) throws ServerErrorException;

}
