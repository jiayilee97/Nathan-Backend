package stacs.nathan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.dto.response.BalanceResponseDto;
import stacs.nathan.entity.Balance;
import stacs.nathan.repository.BalanceRepository;
import stacs.nathan.utils.enums.TokenType;

import java.util.List;

@Service
public class BalanceServiceImpl implements BalanceService {

  @Autowired
  BalanceRepository repository;

  public void createBalance(Balance balance){
    repository.save(balance);
  }

  public List<BalanceResponseDto> fetchBalanceByClient(String clientId){
    return repository.findByClientId(clientId);
  }

  public Balance fetchBalanceByTokenCode(String tokenCode) {
    return repository.findByTokenCode(tokenCode);
  }

  public Balance fetchBalanceByTokenCodeAndId(String tokenCode, Long id) { return repository.findByTokenCodeAndId(tokenCode, id); }

  public Balance fetchBalanceByTokenCodeAndTokenType(String tokenCode, TokenType tokenType) { return repository.findByTokenCodeAndTokenTypeAndIsVisible(tokenCode, tokenType, true); }
}
