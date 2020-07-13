package stacs.nathan.service;

import stacs.nathan.entity.Balance;
import java.util.List;

public interface BalanceService {

  void createBalance(Balance balance);

  List<Balance> fetchBalanceByClient(String clientId);
}
