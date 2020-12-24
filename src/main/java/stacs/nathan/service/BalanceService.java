package stacs.nathan.service;

import stacs.nathan.dto.response.BalanceResponseDto;
import stacs.nathan.entity.Balance;
import stacs.nathan.utils.enums.TokenType;

import java.util.List;

public interface BalanceService {

  void createBalance(Balance balance);

  List<BalanceResponseDto> fetchBalanceByClient(String clientId);

  Balance fetchBalanceByTokenCode(String tokenCode);

  Balance fetchBalanceByTokenCodeAndId(String tokenCode, Long id);

  Balance fetchBalanceByTokenCodeAndTokenType(String tokenCode, TokenType tokenType);
}
