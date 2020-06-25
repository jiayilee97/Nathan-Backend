package stacs.nathan.dto.response;

import java.math.BigDecimal;

public class BCTokenResponseDto {

  private long id;

  private String underlyingCurrency;

  private String tokenCode;

  private int currencyCode;

  private BigDecimal balance;


  public BCTokenResponseDto(long id, String underlyingCurrency, String tokenCode, int currencyCode, BigDecimal balance){
    setId(id);
    setUnderlyingCurrency(underlyingCurrency);
    setTokenCode(tokenCode);
    setCurrencyCode(currencyCode);
    setBalance(balance);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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
