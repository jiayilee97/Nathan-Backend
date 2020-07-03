package stacs.nathan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.entity.Balance;
import stacs.nathan.repository.BalanceRepository;

@Service
public class BalanceServiceImpl implements BalanceService {

  @Autowired
  BalanceRepository repository;

  public void createBalance(Balance balance){
    repository.save(balance);
  }
}
