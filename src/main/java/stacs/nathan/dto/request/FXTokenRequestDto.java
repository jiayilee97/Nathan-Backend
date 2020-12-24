package stacs.nathan.dto.request;

import java.math.BigDecimal;

public class FXTokenRequestDto {

    private String tokenType;

    private String tokenCode;

    private String fxCurrency;

    private int currencyCode;

    private BigDecimal amount;

    private String spTokenCode;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public String getFxCurrency() {
        return fxCurrency;
    }

    public void setFxCurrency(String fxCurrency) {
        this.fxCurrency = fxCurrency;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSpTokenCode() {
        return spTokenCode;
    }

    public void setSpTokenCode(String spTokenCode) {
        this.spTokenCode = spTokenCode;
    }
}
