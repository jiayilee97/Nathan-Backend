package stacs.nathan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.ExchangeRateEntryRequestDto;
import stacs.nathan.entity.*;
import stacs.nathan.repository.*;
import stacs.nathan.utils.enums.FXTokenStatus;
import stacs.nathan.utils.enums.FxCurrency;
import stacs.nathan.utils.enums.TokenType;
import stacs.nathan.utils.enums.UserRole;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

  @Autowired
  ExchangeRateRepository repository;

  @Autowired
  FXTokenRepository fxTokenRepository;

  @Autowired
  BCTokenRepository bcTokenRepository;

  @Autowired
  BalanceRepository balanceRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  InvestorRiskService investorRiskService;

  public void enterExchangeRate(ExchangeRateEntryRequestDto dto) throws ServerErrorException  {
    ExchangeRate exchangeRate = repository.findByCurrencyPair(dto.getCurrencyPair());
    String currencyPair = dto.getCurrencyPair().replace("/", "_");
    FxCurrency currency = FxCurrency.valueOf(currencyPair);
    if (exchangeRate != null) {
      exchangeRate.setCurrency(currency.getValue());
      exchangeRate.setCurrencyPair(dto.getCurrencyPair());
      exchangeRate.setPrice(dto.getPrice());
      repository.save(exchangeRate);
    }
    else {
      ExchangeRate newExchangeRate = new ExchangeRate();
      newExchangeRate.setCurrency(currency.getValue());
      newExchangeRate.setCurrencyPair(dto.getCurrencyPair());
      newExchangeRate.setPrice(dto.getPrice());
      repository.save(newExchangeRate);
    }

    investorRiskService.calculateInvestorRisk();

  }

  public void calculateNAV(ExchangeRateEntryRequestDto dto) throws ServerErrorException {
    String currencyPair = dto.getCurrencyPair().replace("/", "_");
    FxCurrency currency = FxCurrency.valueOf(currencyPair);
    List<User> clientList = userRepository.findByRole(UserRole.CLIENT);

    for (User client: clientList) {
      BigDecimal spNav = calculateSPNav(client, dto);
    }


  }

  public BigDecimal calculateSPNav(User client, ExchangeRateEntryRequestDto dto) {
    String currencyPair = dto.getCurrencyPair().replace("/", "_");
    FxCurrency currency = FxCurrency.valueOf(currencyPair);
    List<FXToken> fxTokenList = fxTokenRepository.findAllByStatusAndSpTokenClientId(FXTokenStatus.OPEN, client.getClientId());
    BigDecimal spNav = BigDecimal.ZERO;

    // Calculate NAV for fx tokens
    for (FXToken fxToken : fxTokenList) {
      String clientId = fxToken.getSpToken().getClientId();
      Balance currentBalance = balanceRepository.findByTokenCodeAndId(fxToken.getTokenCode(), client.getId());
      spNav.add(convertFX(currentBalance, dto.getPrice(), currency.getValue()));
    }
    return spNav;
  }

  public BigDecimal convertFX(Balance balance, BigDecimal exchangeRate, String currency) {
    BigDecimal newNav = BigDecimal.ZERO;
    System.out.println("exchangeRate:" + exchangeRate);
    System.out.println(currency);
    switch (currency) {
      case "AUD" :
      case "EUR" :
      case "GBP" : newNav = balance.getBalanceAmount().multiply(exchangeRate); break;
      case "USD" : newNav = balance.getBalanceAmount(); break;
      case "JPY" :
      case "CHF" : newNav = balance.getBalanceAmount().divide(exchangeRate); break;
    }

    return newNav;
  }

}
