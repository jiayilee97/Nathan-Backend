package stacs.nathan.utils.enums;

import stacs.nathan.dto.Select;

public enum FixingType {
  DAILY_FIXING("Daily Fixing"), WEEKLY_FIXING("Weekly Fixing"), MONTHLY_FIXING("Monthly Fixing");

  private String value;

  FixingType(String value) {
    this.value = value;
  }

  public static String resolveValue(String value) {
    for (FixingType type : values()) {
      if (type.getValue().equalsIgnoreCase(value)) {
        return type.name();
      }
    }
    return value;
  }

  public static FixingType resolveCode(String code) {
    for (FixingType type : values()) {
      if (type.getCode().equals(code)) {
        return type;
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
    for (FixingType type: FixingType.values()) {
      selection.addOption(type.value, type.name());
    }
    return selection;
  }
}
