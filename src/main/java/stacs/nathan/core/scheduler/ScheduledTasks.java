package stacs.nathan.core.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stacs.nathan.service.BCTokenService;
import java.util.Date;

@Component
public class ScheduledTasks {
  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

  @Autowired
  BCTokenService bcTokenService;

  /**
   * Blockchain confirmation Task for BCToken which runs every hour
   */
  @Scheduled(fixedRate=60*60*1000)
  public void confirmBCToken() {
    LOGGER.debug("Entering confirmBCToken() method. {}", new Date());
    bcTokenService.execute();
  }


}
