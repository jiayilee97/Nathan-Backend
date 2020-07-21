package stacs.nathan.core.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.service.BCTokenService;
import stacs.nathan.service.FXTokenService;
import stacs.nathan.service.SPTokenService;
import java.util.Date;

@Component
public class ScheduledTasks {
  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

  private final String system = "SCHEDULAR";

  @Autowired
  BCTokenService bcTokenService;

  @Autowired
  SPTokenService spTokenService;

  @Autowired
  FXTokenService fxTokenService;

  /**
   * Blockchain confirmation Task for BCToken which runs every hour
   */
  @Scheduled(fixedRate=60*60*1000)
  public void executeBCTokenUnconfirmedChain() {
    LOGGER.debug("Entering executeBCTokenUnconfirmedChain() method. {}", new Date());
    bcTokenService.executeUnconfirmedChain(system);
  }

  @Scheduled(fixedRate=60*60*1000)
  public void executeBCTokenUnavailableChain() {
    LOGGER.debug("Entering executeBCTokenUnavailableChain() method. {}", new Date());
    bcTokenService.executeUnavailableChain(system);
  }

  /**
   * Blockchain confirmation Task for SPToken which runs every hour
   */
  @Scheduled(fixedRate=60*60*1000)
  public void executeSPTokenUnconfirmedChain() {
    LOGGER.debug("Entering executeSPTokenUnconfirmedChain() method. {}", new Date());
    spTokenService.executeUnconfirmedChain(system);
  }

  @Scheduled(fixedRate=60*60*1000)
  public void executeSPTokenUnavailableChain() {
    LOGGER.debug("Entering executeSPTokenUnavailableChain() method. {}", new Date());
    spTokenService.executeUnavailableChain(system);
  }

  @Scheduled(fixedRate=1*60*1000)
  public void checkSPTokenMaturity() throws ServerErrorException {
    LOGGER.debug("Entering checkSPTokenMaturity() method. {}", new Date());
    spTokenService.checkSPTokenMaturity(system);
  }

  /**
   * Blockchain confirmation Task for FXToken which runs every hour
   */
  @Scheduled(fixedRate=60*60*1000)
  public void confirmFXTokenUnconfirmedChain() {
    LOGGER.debug("Entering confirmFXTokenUnconfirmedChain() method. {}", new Date());
    fxTokenService.executeUnconfirmedChain(system);
  }

  @Scheduled(fixedRate=60*60*1000)
  public void executeFXTokenUnavailableChain() {
    LOGGER.debug("Entering executeFXTokenUnavailableChain() method. {}", new Date());
    fxTokenService.executeUnavailableChain(system);
  }
}
