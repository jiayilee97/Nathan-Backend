package stacs.nathan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.ClientResponseDto;
import stacs.nathan.dto.response.ExchangeRateResponseDto;
import stacs.nathan.dto.response.InvestorRiskResponseDto;
import stacs.nathan.entity.*;
import stacs.nathan.repository.*;
import stacs.nathan.utils.enums.FxCurrency;
import stacs.nathan.utils.enums.TokenType;
import stacs.nathan.utils.enums.UserRole;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class InvestorRiskServiceImpl implements InvestorRiskService {
  private static final Logger LOGGER = LoggerFactory.getLogger(InvestorRiskServiceImpl.class);

  @Autowired
  InvestorRiskRepository repository;

  @Autowired
  NAVService navService;

  @Autowired
  UserService userService;

  @Autowired
  SPTokenService spTokenService;

  @Autowired
  FXTokenRepository fxTokenRepository;

  @Autowired
  ExchangeRateRepository exchangeRateRepository;

  @Autowired
  BalanceRepository balanceRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  BCTokenRepository bcTokenRepository;

  public InvestorRiskResponseDto fetchAllInvestorRisk() throws ServerErrorException {
    LOGGER.debug("Entering fetchAllInvestorRisk().");
    try{
      InvestorRiskResponseDto dto = new InvestorRiskResponseDto();
      List<InvestorRisk> investorRisks = repository.findAll();
      HashMap<String, InvestorRisk> clientDataMap = new HashMap<>();
      for (InvestorRisk risk : investorRisks) {
        if (!clientDataMap.containsKey(risk.getClientId())) {
          clientDataMap.put(risk.getClientId(), risk);
        } else {
          Date latestDate = clientDataMap.get(risk.getClientId()).getUpdatedDate();
          if (risk.getUpdatedDate().after(latestDate)) {
            clientDataMap.replace(risk.getClientId(), risk);
          }
        }
      }

      List<InvestorRisk> latestData = new ArrayList<InvestorRisk>(clientDataMap.values());


      dto.setInvestorRisks(latestData);
      dto.setTotalCurrentNAV(navService.fetchCurrentNAV());
      return dto;
    } catch (Exception e){
      LOGGER.error("Exception in fetchAllInvestorRisk().", e);
      throw new ServerErrorException("Exception in fetchAllInvestorRisk().", e);
    }
  }

  public void save(InvestorRisk investorRisks) throws ServerErrorException {
    LOGGER.debug("Entering save().");
    try{
      repository.save(investorRisks);
    } catch (Exception e){
      LOGGER.error("Exception in save().", e);
      throw new ServerErrorException("Exception in save().", e);
    }
  }

  public void saveAll(List<InvestorRisk> investorRisks) throws ServerErrorException {
    LOGGER.debug("Entering saveAll().");
    try{
      repository.saveAll(investorRisks);
    } catch (Exception e){
      LOGGER.error("Exception in saveAll().", e);
      throw new ServerErrorException("Exception in saveAll().", e);
    }
  }

  @Transactional(rollbackFor = ServerErrorException.class)
  public void calculateInvestorRisk() throws ServerErrorException {
    LOGGER.debug("Entering calculateInvestorRisk().");
    try{
      List<User> clients = userRepository.findByRole(UserRole.CLIENT);
      List<InvestorRisk> investorRisks = new ArrayList<>();
      BigDecimal totalNAV = BigDecimal.ZERO;
      for(User  client : clients) {
        String clientId = client.getClientId();
        List<String> currencyPairs = exchangeRateRepository.findAllUniqueCurrency();
        List<ExchangeRate> exchangeRates = exchangeRateRepository.fetchCurrentExchangeRates();
        InvestorRisk investorRisk = new InvestorRisk();

        // calculating NAV of SP Token
        // TODO : retrieve the currencyPair list from ExchangeRate table
        List<SPToken> spTokens = spTokenService.fetchAllOpenPositionsForRisk(clientId, currencyPairs);
        BigDecimal navSPToken = BigDecimal.ZERO;
        BigDecimal navInvestedAmount = BigDecimal.ZERO;
        for(SPToken spToken : spTokens){
          // TODO : convert notional amount to USD and add to navSPToken
          BigDecimal convertedAmount = convertSPToken(spToken.getNotionalAmount(), spToken.getUnderlyingCurrency(), exchangeRates);
          navSPToken = navSPToken.add(convertedAmount);

          // calculating Invested amount
          if (spToken.getFxToken() != null) {
            Balance fxBalance = balanceRepository.findByTokenCodeAndId(spToken.getFxToken().getTokenCode(), client.getId());
            BigDecimal convertedInvestment = convertInvestedAmount(fxBalance.getBalanceAmount(), spToken.getFxToken().getFxCurrency(), exchangeRates);
            navInvestedAmount = navInvestedAmount.add(convertedInvestment);
          }
        }

        // calculating bcToken balance
        List<Balance> bcTokens = balanceRepository.findByTokenTypeAndUser(TokenType.BC_TOKEN, client);
        BigDecimal navBcToken = BigDecimal.ZERO;
        for (Balance bcBalance: bcTokens) {
          BaseCurrencyToken baseCurrencyToken = bcTokenRepository.findByTokenCode(bcBalance.getTokenCode());
          BigDecimal convertedBcAmount = convertToUSD(bcBalance.getBalanceAmount(), baseCurrencyToken.getUnderlyingCurrency(), exchangeRates);
          navBcToken = navBcToken.add(convertedBcAmount);
        }
        investorRisk.setNavSPToken(navSPToken);
        investorRisk.setInvestedAmount(navInvestedAmount);
        investorRisk.setClientId(clientId);
        investorRisk.setBcTokenBalance(navBcToken);
        totalNAV = totalNAV.add(navSPToken);
        totalNAV = totalNAV.add(navInvestedAmount);
        totalNAV = totalNAV.add(navBcToken);

        investorRisks.add(investorRisk);
      }
      // save investor risk
      repository.saveAll(investorRisks);
      // save total NAV
      navService.save(totalNAV);
    } catch (Exception e){
      LOGGER.error("Exception in calculateInvestorRisk().", e);
      throw new ServerErrorException("Exception in calculateInvestorRisk().", e);
    }
  }

  public BigDecimal convertToUSD(BigDecimal balance, String currency, List<ExchangeRate> exchangeRates) {
    BigDecimal newNav = BigDecimal.ZERO;
    BigDecimal exchangeRate = BigDecimal.ONE;
    for (ExchangeRate rate: exchangeRates) {
      if (currency.compareTo(rate.getCurrency()) == 0) {
        exchangeRate = rate.getPrice();
        break;
      }
    }

    switch (currency) {
      case "AUD" :
      case "EUR" :
      case "GBP" : newNav = balance.multiply(exchangeRate); break;
      case "USD" : newNav = balance; break;
      case "JPY" :
      case "CHF" : newNav = balance.divide(exchangeRate); break;
    }

    return newNav;
  }

  public BigDecimal convertInvestedAmount(BigDecimal balance, String currency, List<ExchangeRate> exchangeRates) {
    BigDecimal newNav = BigDecimal.ZERO;
    BigDecimal exchangeRate = BigDecimal.ONE;
    for (ExchangeRate rate: exchangeRates) {
      if (currency.compareTo(rate.getCurrency()) == 0) {
        exchangeRate = rate.getPrice();
        break;
      }
    }
    switch (currency) {
      case "AUD" :
      case "EUR" :
      case "GBP" : newNav = balance.multiply(exchangeRate); break;
      case "USD" :
      case "JPY" :
      case "CHF" : newNav = balance; break;
    }

    return newNav;
  }

  public BigDecimal convertSPToken(BigDecimal balance, String currencyPair, List<ExchangeRate> exchangeRates) {
    BigDecimal newNav = BigDecimal.ZERO;
    FxCurrency fxCurrency = FxCurrency.valueOf(currencyPair.replace("/", "_"));
    String currency = fxCurrency.getValue();
    BigDecimal exchangeRate = BigDecimal.ONE;
    for (ExchangeRate rate: exchangeRates) {
      if (currency.compareTo(rate.getCurrency()) == 0) {
        exchangeRate = rate.getPrice();
        break;
      }
    }

    switch (currency) {
      case "AUD" :
      case "EUR" :
      case "GBP" : newNav = balance.multiply(exchangeRate); break;
      case "USD" :
      case "JPY" :
      case "CHF" : newNav = balance; break;
    }

    return newNav;
  }

}
