package stacs.nathan.core.exception;

public class ServerErrorException extends Exception {
  public ServerErrorException(String message) {
    super(message);
  }

  public ServerErrorException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServerErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
