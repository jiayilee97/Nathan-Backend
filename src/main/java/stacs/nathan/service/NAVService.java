package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.entity.NAV;
import java.math.BigDecimal;
import java.util.List;

public interface NAVService {

  BigDecimal fetchCurrentNAV() throws ServerErrorException;

  List<NAV> fetchAllNAV(String startDate, String endDate) throws ServerErrorException;

}
