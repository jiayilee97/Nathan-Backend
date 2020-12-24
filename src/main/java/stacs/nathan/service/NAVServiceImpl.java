package stacs.nathan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.entity.NAV;
import stacs.nathan.repository.NAVRepository;
import stacs.nathan.utils.CommonUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class NAVServiceImpl implements NAVService {
  private static final Logger LOGGER = LoggerFactory.getLogger(NAVServiceImpl.class);

  @Autowired
  private NAVRepository repository;

  public NAV fetchCurrentNAV() throws ServerErrorException {
    LOGGER.debug("Entering fetchCurrentNAV().");
    try{
      return repository.findFirstByOrderByIdDesc();
    } catch (Exception e){
      LOGGER.error("Exception in fetchCurrentNAV().", e);
      throw new ServerErrorException("Exception in fetchCurrentNAV().", e);
    }
  }

  // fetch all except current NAV
  public List<NAV> fetchAllNAV(String startDate, String endDate) throws ServerErrorException {
    LOGGER.debug("Entering fetchAllNAV().");
    try{
      Date start = CommonUtils.formatDate(startDate, -1);
      Date end = CommonUtils.formatDate(endDate, 1);
      return repository.fetchAllNAV(start, end);
    } catch (Exception e){
      LOGGER.error("Exception in fetchAllNAV().", e);
      throw new ServerErrorException("Exception in fetchAllNAV().", e);
    }
  }

  public void save(BigDecimal totalNAV) throws ServerErrorException {
    LOGGER.debug("Entering save().");
    try{
      NAV nav = new NAV();
      nav.setAssetValue(totalNAV);
      System.out.println("totalNav" + totalNAV);
      repository.save(nav);
    } catch (Exception e){
      LOGGER.error("Exception in save().", e);
      throw new ServerErrorException("Exception in save().", e);
    }
  }

}
