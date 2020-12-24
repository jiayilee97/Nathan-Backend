package stacs.nathan.dto.request;

import java.math.BigDecimal;
import java.util.Date;

public class FXTokenDataEntryRequestDto {
    private String fxTokenCode;
    private String fxCurrency;
    private BigDecimal price;
    private Date entryDate;

    public FXTokenDataEntryRequestDto(String fxTokenCode, String fxCurrency, BigDecimal price, Date entryDate) {
        this.fxTokenCode = fxTokenCode;
        this.fxCurrency = fxCurrency;
        this.price = price;
        this.entryDate = entryDate;
    }

    public String getFxTokenCode() {
        return fxTokenCode;
    }

    public void setFxTokenCode(String fxTokenCode) {
        this.fxTokenCode = fxTokenCode;
    }

    public String getFxCurrency() {
        return fxCurrency;
    }

    public void setFxCurrency(String fxCurrency) {
        this.fxCurrency = fxCurrency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
}
