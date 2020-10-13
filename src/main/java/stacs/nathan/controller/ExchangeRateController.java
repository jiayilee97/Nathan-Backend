package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.ExchangeRateEntryRequestDto;
import stacs.nathan.entity.ExchangeRate;
import stacs.nathan.service.ExchangeRateService;
import java.util.List;

@RestController
@RequestMapping("/exchange-rate")
@PreAuthorize("hasAnyAuthority('OPS', 'CRO')")
public class ExchangeRateController {

  @Autowired
  ExchangeRateService exchangeRateService;

  @RequestMapping("/enter-exchange-rate")
  public void enterExchangeRate(@RequestBody List<ExchangeRateEntryRequestDto> dto) throws ServerErrorException {
    exchangeRateService.enterExchangeRate(dto);
  }

  @RequestMapping("/fetch-all")
  public List<ExchangeRate> fetchExchangeRate(@RequestParam String startDate, @RequestParam String endDate) throws ServerErrorException {
    return exchangeRateService.fetchExchangeRate(startDate, endDate);
  }

  @RequestMapping("/fetch-updated")
  public List<ExchangeRate> fetchLatestExchangeRate() throws ServerErrorException { return exchangeRateService.fetchLatestExchangeRate(); }

}
