package stacs.nathan.core.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stacs.nathan.service.BCTokenService;
import stacs.nathan.service.SPTokenService;

import java.util.Date;

@Component
public class ScheduledTasks {
  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

  @Autowired
  BCTokenService bcTokenService;

  @Autowired
  SPTokenService spTokenService;

  /**
   * Blockchain confirmation Task for BCToken which runs every hour
   */
  @Scheduled(fixedRate=60*60*1000)
  public void executeUnconfirmedChain() {
    LOGGER.debug("Entering confirmBCToken() method. {}", new Date());
    bcTokenService.executeUnconfirmedChain();
  }

  @Scheduled(fixedRate=60*60*1000)
  public void executeUnavailableChain() {
    LOGGER.debug("Entering executeUnavailableChain() method. {}", new Date());
    bcTokenService.executeUnavailableChain();
  }

  /**
   * Blockchain confirmation Task for SPToken which runs every hour
   */
  @Scheduled(fixedRate=60*60*1000)
  public void confirmSPToken() {
    LOGGER.debug("Entering confirmSPToken() method. {}", new Date());
    spTokenService.execute();
  }

  @Scheduled(fixedRate=3*60*1000)
  public void checkSPTokenMaturity() {
    LOGGER.debug("Entering checkSPTokenMaturity() method. {}", new Date());
    spTokenService.checkSPTokenMaturity();
  }
}
