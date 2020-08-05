package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.ExchangeRateEntryRequestDto;
import stacs.nathan.service.ExchangeRateService;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {

  @Autowired
  ExchangeRateService exchangeRateService;

  @PreAuthorize("hasAuthority('OPS')")
  @RequestMapping("/enter-exchange-rate")
  public void enterExchangeRate(@RequestBody ExchangeRateEntryRequestDto dto) throws ServerErrorException {
    exchangeRateService.enterExchangeRate(dto);
  }

}
