package stacs.nathan.dto.response;

import java.math.BigDecimal;
import java.util.Date;

public class FXTokenDataEntryResponseDto {
    private String clientId;
    private String fxTokenCode;
    private BigDecimal price;
    private String currency;
    private Date entryDate;

    public FXTokenDataEntryResponseDto(String clientId, String fxTokenCode, BigDecimal price, String currency, Date entryDate) {
        this.clientId = clientId;
        this.fxTokenCode = fxTokenCode;
        this.price = price;
        this.currency = currency;
        this.entryDate = entryDate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFxTokenCode() {
        return fxTokenCode;
    }

    public void setFxTokenCode(String fxTokenCode) {
        this.fxTokenCode = fxTokenCode;
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
