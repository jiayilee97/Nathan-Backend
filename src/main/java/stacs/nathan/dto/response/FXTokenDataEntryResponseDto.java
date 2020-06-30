package stacs.nathan.dto.response;

import java.math.BigDecimal;
import java.util.Date;

public class FXTokenDataEntryResponseDto {
    private BigDecimal price;
    private String currency;
    private Date entryDate;

    public FXTokenDataEntryResponseDto(BigDecimal price, String currency, Date entryDate) {
        this.price = price;
        this.currency = currency;
        this.entryDate = entryDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
}
