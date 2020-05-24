package stacs.nathan.utils;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.UUID;

public abstract class CommonUtils {

  public static void afterCommit(Runnable runnable) {
    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
      @Override
      public void afterCommit() {
        if (runnable != null) {
          runnable.run();
        }
      }
    });
  }

  public static String generateRandomUUID(){
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

}
