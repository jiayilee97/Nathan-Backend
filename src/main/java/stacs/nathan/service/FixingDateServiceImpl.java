package stacs.nathan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.entity.FixingDate;
import stacs.nathan.entity.SPToken;
import stacs.nathan.repository.FixingDateRepository;
import java.util.Date;
import java.util.List;

@Service
public class FixingDateServiceImpl implements FixingDateService {
  private static final Logger LOGGER = LoggerFactory.getLogger(FixingDateServiceImpl.class);

  @Autowired
  private FixingDateRepository repository;

  public void saveFixingDates(List<FixingDate> fixingDates) throws ServerErrorException {
    LOGGER.debug("Entering saveFixingDates().");
    try {
      repository.saveAll(fixingDates);
    } catch (Exception e){
      LOGGER.error("Exception in saveFixingDates().", e);
      throw new ServerErrorException("Exception in saveFixingDates().", e);
    }
  }

  public List<SPToken> fetchByFixingDatesAndCurrency(Date startDate, Date endDate, String currency) throws ServerErrorException {
    LOGGER.debug("Entering fetchByFixingDatesAndCurrency().");
    try {
      return repository.findByFixingDate(startDate, endDate, currency);
    } catch (Exception e){
      LOGGER.error("Exception in fetchByFixingDatesAndCurrency().", e);
      throw new ServerErrorException("Exception in fetchByFixingDatesAndCurrency().", e);
    }
  }
}
