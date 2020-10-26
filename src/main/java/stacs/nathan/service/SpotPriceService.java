package stacs.nathan.service;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.FXTokenDataEntryRequestDto;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SpotPriceService {
  private static final Logger LOGGER = LoggerFactory.getLogger(SpotPriceService.class);

  @Autowired
  private UserService userService;

  @Autowired
  private FixingDateService fixingDateService;

  @Autowired
  private FXTokenService fxTokenService;

  public List<String> processSpotPrice(FXTokenDataEntryRequestDto dto) throws ServerErrorException {
    LOGGER.debug("Entering processSpotPrice().");
    try {
      Date entryDate = dto.getEntryDate();
      User ops = userService.fetchLoginUser();
      List<String> result = new ArrayList<>();
      List<SPToken> spTokens = fixingDateService.fetchByFixingDatesAndCurrency(entryDate, DateUtils.addDays(entryDate, 1), dto.getFxCurrency());
      if(spTokens != null && spTokens.size() > 0) {
        for(SPToken spToken: spTokens) {
          try {
            fxTokenService.processTradeTransfer(spToken, dto, ops, result);
          } catch (Exception e) {
            LOGGER.error("Exception in processSpotPrice(). SPToken: ", spToken.getTokenCode());
          }
        }
      }
      return result;
    } catch (Exception e) {
      LOGGER.error("Exception in processSpotPrice().", e);
      throw new ServerErrorException("Exception in processSpotPrice().", e);
    }
  }

}
