package stacs.nathan.dto.response;

import java.math.BigDecimal;

public class BCTokenResponseDto {

  private String underlyingCurrency;

  private String tokenCode;

  private int currencyCode;

  private BigDecimal balance;


  public BCTokenResponseDto(String underlyingCurrency, String tokenCode, int currencyCode, BigDecimal balance){
    setUnderlyingCurrency(underlyingCurrency);
    setTokenCode(tokenCode);
    setCurrencyCode(currencyCode);
    setBalance(balance);
  }

  public String getUnderlyingCurrency() {
    return underlyingCurrency;
  }

  public void setUnderlyingCurrency(String underlyingCurrency) {
    this.underlyingCurrency = underlyingCurrency;
  }

  public String getTokenCode() {
    return tokenCode;
  }

  public void setTokenCode(String tokenCode) {
    this.tokenCode = tokenCode;
  }

  public int getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(int currencyCode) {
    this.currencyCode = currencyCode;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
}
