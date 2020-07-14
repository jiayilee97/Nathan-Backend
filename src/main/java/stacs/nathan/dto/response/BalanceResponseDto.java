package stacs.nathan.dto.response;

import stacs.nathan.utils.enums.TokenType;

import java.math.BigDecimal;

public class BalanceResponseDto {

  private String tokenCode;

  private String tokenType;

  private BigDecimal balance;

  public BalanceResponseDto(String tokenCode, TokenType tokenType, BigDecimal balance) {
    setTokenCode(tokenCode);
    setTokenType(tokenType.getValue());
    setBalance(balance);
  }

  public String getTokenCode() {
    return tokenCode;
  }

  public void setTokenCode(String tokenCode) {
    this.tokenCode = tokenCode;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
}
