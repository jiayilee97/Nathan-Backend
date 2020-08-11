package stacs.nathan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.ExchangeRateEntryRequestDto;
import stacs.nathan.entity.*;
import stacs.nathan.repository.*;
import stacs.nathan.utils.CommonUtils;
import stacs.nathan.utils.enums.FxCurrency;
import java.util.Date;
import java.util.List;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

  @Autowired
  ExchangeRateRepository repository;

  @Autowired
  InvestorRiskService investorRiskService;


  public void enterExchangeRate(List<ExchangeRateEntryRequestDto> exchangeRateList) throws ServerErrorException {
        LOGGER.debug("Entering enterExchangeRate().");
        try {
          for (ExchangeRateEntryRequestDto dto : exchangeRateList) {
            //ExchangeRate exchangeRate = repository.findByCurrencyPair(dto.getCurrencyPair());
            String currencyPair = dto.getCurrencyPair().replace("/", "_");
            FxCurrency currency = FxCurrency.valueOf(currencyPair);

            ExchangeRate newExchangeRate = new ExchangeRate();
            newExchangeRate.setCurrency(currency.getValue());
            newExchangeRate.setCurrencyPair(dto.getCurrencyPair());
            newExchangeRate.setPrice(dto.getPrice());
            repository.save(newExchangeRate);
          }

          investorRiskService.calculateInvestorRisk();

        } catch (Exception e) {
          LOGGER.error("Exception in enterExchangeRate().", e);
          throw new ServerErrorException("Exception in enterExchangeRate().", e);
        }
  }

  public List<ExchangeRate> fetchLatestExchangeRate() {
    return repository.fetchUpdatedExchangeRates();
  }


  public List<ExchangeRate> fetchExchangeRate(String startDate, String endDate) throws ServerErrorException {
    LOGGER.debug("Entering fetchExchangeRate().");
    try {
      Date start = CommonUtils.formatDate(startDate, -1);
      Date end = CommonUtils.formatDate(endDate, 1);
      return repository.fetchCurrentExchangeRates(start, end);
    }
    catch (Exception e) {
      LOGGER.error("Exception in fetchExchangeRate().", e);
      throw new ServerErrorException("Exception in fetchExchangeRate().", e);
    }
  }

}
