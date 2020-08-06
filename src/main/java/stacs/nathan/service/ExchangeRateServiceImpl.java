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

  public void enterExchangeRate(List<ExchangeRateEntryRequestDto> exchangeRateList) throws ServerErrorException  {
    for (ExchangeRateEntryRequestDto dto: exchangeRateList) {
      ExchangeRate exchangeRate = repository.findByCurrencyPair(dto.getCurrencyPair());
      String currencyPair = dto.getCurrencyPair().replace("/", "_");
      FxCurrency currency = FxCurrency.valueOf(currencyPair);
      if (exchangeRate != null) {
        exchangeRate.setCurrency(currency.getValue());
        exchangeRate.setCurrencyPair(dto.getCurrencyPair());
        exchangeRate.setPrice(dto.getPrice());
        repository.save(exchangeRate);
      } else {
        ExchangeRate newExchangeRate = new ExchangeRate();
        newExchangeRate.setCurrency(currency.getValue());
        newExchangeRate.setCurrencyPair(dto.getCurrencyPair());
        newExchangeRate.setPrice(dto.getPrice());
        repository.save(newExchangeRate);
      }
    }

    investorRiskService.calculateInvestorRisk();

  }

  public List<ExchangeRate> fetchExchangeRate() {
    return repository.fetchCurrentExchangeRates();
  }

}
