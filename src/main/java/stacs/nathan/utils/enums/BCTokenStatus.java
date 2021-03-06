package stacs.nathan.utils.enums;

public enum BCTokenStatus {
  OPEN("Open"),
  CLOSED("Closed"),
  FAILED_IN_CHAIN("Failed in chain"),
  UNCONFIRMED_IN_CHAIN("Unconfirmed in chain"),
  CHAIN_UNAVAILABLE("Chain unavailable");

  private String value;

  BCTokenStatus(String value) {
    this.value = value;
  }

  public static String resolveValue(String value) {
    for (BCTokenStatus status : values()) {
      if (status.getValue().equalsIgnoreCase(value)) {
        return status.name();
      }
    }
    return value;
  }

  public static BCTokenStatus resolveCode(String code) {
    for (BCTokenStatus status : values()) {
      if (status.getCode().equals(code)) {
        return status;
      }
    }
    return null;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getCode() {
    return name();
  }
}
