package stacs.nathan.utils.enums;

public enum CodeType {
  UNDERLYING("Underlying");

  private String value;

  CodeType(String value) {
    this.value = value;
  }

  public static String resolveValue(String value) {
    for (CodeType type : values()) {
      if (type.getValue().equalsIgnoreCase(value)) {
        return type.name();
      }
    }
    return value;
  }

  public static CodeType resolveCode(String code) {
    for (CodeType type : values()) {
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

}
