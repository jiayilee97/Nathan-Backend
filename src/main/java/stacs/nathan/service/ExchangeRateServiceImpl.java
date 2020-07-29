package stacs.nathan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.repository.ExchangeRateRepository;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

  @Autowired
  ExchangeRateRepository repository;

}
