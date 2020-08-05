package stacs.nathan.dto.request;

import java.math.BigDecimal;

public class ExchangeRateEntryRequestDto {

    private String currencyPair;
    private BigDecimal price;

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
