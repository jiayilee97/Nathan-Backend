package stacs.nathan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.ClientResponseDto;
import stacs.nathan.dto.response.InvestorRiskResponseDto;
import stacs.nathan.entity.BaseCurrencyToken;
import stacs.nathan.entity.InvestorRisk;
import stacs.nathan.entity.SPToken;
import stacs.nathan.repository.InvestorRiskRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
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

  public InvestorRiskResponseDto fetchAllInvestorRisk() throws ServerErrorException {
    LOGGER.debug("Entering fetchAllInvestorRisk().");
    try{
      InvestorRiskResponseDto dto = new InvestorRiskResponseDto();
      dto.setInvestorRisks(repository.findAll());
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
      List<ClientResponseDto> clients = userService.fetchAllClients();
      List<InvestorRisk> investorRisks = new ArrayList<>();
      BigDecimal totalNAV = BigDecimal.ZERO;
      for(ClientResponseDto client : clients) {
        String clientId = client.getClientId();

        // calculating NAV of SP Token
        // TODO : retrieve the currencyPair list from ExchangeRate table
        List<String> currencyPairs = new ArrayList<>();
        InvestorRisk investorRisk = new InvestorRisk();
        List<SPToken> spTokens = spTokenService.fetchAllOpenPositionsForRisk(clientId, currencyPairs);
        BigDecimal navSPToken = BigDecimal.ZERO;
        for(SPToken spToken : spTokens){
          // TODO : convert notional amount to USD and add to navSPToken
          navSPToken.add(spToken.getNotionalAmount());
        }
        investorRisk.setNavSPToken(navSPToken);
        totalNAV.add(navSPToken);

        // calculating Investor Risk


        // calculating bcToken balance


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

}
