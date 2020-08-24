package stacs.nathan.utils.enums;

import stacs.nathan.dto.Select;

public enum TenorType {
  DAYS("Days"), WEEKS("Weeks");

  private String value;

  TenorType(String value) {
    this.value = value;
  }

  public static String resolveValue(String value) {
    for (TenorType type : values()) {
      if (type.getValue().equalsIgnoreCase(value)) {
        return type.name();
      }
    }
    return value;
  }

  public static TenorType resolveCode(String code) {
    for (TenorType type : values()) {
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
    for (TenorType type: TenorType.values()) {
      selection.addOption(type.value, type.name());
    }
    return selection;
  }
}
