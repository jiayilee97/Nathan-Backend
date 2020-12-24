package stacs.nathan.utils.enums;

import stacs.nathan.dto.Select;

public enum AccreditedStatus {
  RETAIL("Retail"), ACCREDITED("Accredited");

  private String value;

  AccreditedStatus(String value) {
    this.value = value;
  }

  public static String resolveValue(String value) {
    for (AccreditedStatus status : values()) {
      if (status.getValue().equalsIgnoreCase(value)) {
        return status.name();
      }
    }
    return value;
  }

  public static AccreditedStatus resolveCode(String code) {
    for (AccreditedStatus status : values()) {
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

  public static Select getValuesSelection() {
    Select selection = new Select();
    for (AccreditedStatus accreditedStatus: AccreditedStatus.values()) {
      selection.addOption(accreditedStatus.value, accreditedStatus.name());
    }
    return selection;
  }

}
