package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.entity.BaseCurrencyToken;

public interface BCTokenService {

  void createBCToken(BaseCurrencyToken token) throws ServerErrorException;

}
