package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.entity.FixingDate;
import java.util.List;

public interface FixingDateService {

  void saveFixingDates(List<FixingDate> fixingDate) throws ServerErrorException;

}
