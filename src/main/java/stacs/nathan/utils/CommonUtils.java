package stacs.nathan.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

  public static Date formatDate(String date, int amount) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    return DateUtils.addDays(formatter.parse(date), amount);
  }

}
