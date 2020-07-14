package stacs.nathan.dto.response;

import stacs.nathan.utils.enums.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionHistoryResponseDto {
    private String tokenCode;
    private BigDecimal amount;
    private String targetAddress;
    private TransactionStatus status;
    private String txId;
    private Date date;

    public TransactionHistoryResponseDto(String tokenCode, BigDecimal amount, String targetAddress, TransactionStatus status, String txId, Date date) {
        this.tokenCode = tokenCode;
        this.amount = amount;
        this.targetAddress = targetAddress;
        this.status = status;
        this.txId = txId;
        this.date = date;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
