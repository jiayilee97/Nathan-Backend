package stacs.nathan.dto.request;

import java.math.BigDecimal;

public class BCTokenRequestDto {

  private String underlyingCurrency;

  private String tokenCode;

  private int currencyCode;

  private BigDecimal amount;

  public String getUnderlyingCurrency() {
    return underlyingCurrency;
  }

  public String getTokenCode() {
    return tokenCode;
  }

  public int getCurrencyCode() {
    return currencyCode;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
