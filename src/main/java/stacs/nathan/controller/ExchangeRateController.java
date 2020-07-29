package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.service.ExchangeRateService;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {

  @Autowired
  ExchangeRateService exchangeRateService;


}
