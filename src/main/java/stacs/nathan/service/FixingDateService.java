package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.entity.FixingDate;
import stacs.nathan.entity.SPToken;
import java.util.Date;
import java.util.List;

public interface FixingDateService {

  void saveFixingDates(List<FixingDate> fixingDates) throws ServerErrorException;

  List<SPToken> fetchByFixingDatesAndCurrency(Date startDate, Date endDate, String currency) throws ServerErrorException;

}
