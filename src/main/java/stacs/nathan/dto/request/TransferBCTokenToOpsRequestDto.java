package stacs.nathan.dto.request;

import java.math.BigDecimal;

public class TransferBCTokenToOpsRequestDto {
    private String bcTokenCode;
    private BigDecimal amount;
    private String senderAddress;
    private String recepientAddress;
    private String fxTokenCode;

    public String getBcTokenCode() {
        return bcTokenCode;
    }

    public void setBcTokenCode(String bcTokenCode) {
        this.bcTokenCode = bcTokenCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRecepientAddress() {
        return recepientAddress;
    }

    public void setRecepientAddress(String recepientAddress) {
        this.recepientAddress = recepientAddress;
    }

    public String getFxTokenCode() {
        return fxTokenCode;
    }

    public void setFxTokenCode(String fxTokenCode) {
        this.fxTokenCode = fxTokenCode;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }
}
