package stacs.nathan.dto.response;

import java.math.BigDecimal;

public class ExchangeRateResponseDto {
    private String currencyPair;
    private String currency;
    private BigDecimal price;

    public ExchangeRateResponseDto(String currencyPair, String currency, BigDecimal price) {
        this.currencyPair = currencyPair;
        this.currency = currency;
        this.price = price;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
