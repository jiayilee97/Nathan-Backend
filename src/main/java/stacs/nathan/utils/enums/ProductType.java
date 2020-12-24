package stacs.nathan.utils.enums;

import stacs.nathan.dto.Select;

public enum ProductType {

  NON_BOOSTED_ACCFW("Non-Boosted Accumulator Forward");

  private String value;

  ProductType(String value) {
    this.value = value;
  }

  public static String resolveValue(String value) {
    for (ProductType type : values()) {
      if (type.getValue().equalsIgnoreCase(value)) {
        return type.name();
      }
    }
    return value;
  }

  public static ProductType resolveCode(String code) {
    for (ProductType type : values()) {
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
    for (ProductType productType: ProductType.values()) {
      selection.addOption(productType.value, productType.name());
    }
    return selection;
  }
}
