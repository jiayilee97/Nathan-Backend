package stacs.nathan.service;

import stacs.nathan.dto.response.BalanceResponseDto;
import stacs.nathan.entity.Balance;
import java.util.List;

public interface BalanceService {

  void createBalance(Balance balance);

  List<BalanceResponseDto> fetchBalanceByClient(String clientId);

  Balance fetchBalanceByTokenCode(String tokenCode);
}
