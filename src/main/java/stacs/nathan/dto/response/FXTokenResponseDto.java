package stacs.nathan.dto.response;

import stacs.nathan.utils.enums.FXTokenStatus;

import java.math.BigDecimal;
import java.util.Date;

public class FXTokenResponseDto {
    private String fxTokenCode;
    private String spTokenCode;
    private Date contractInceptionDate;
    private Date maturityDate;
    private BigDecimal balance;
    private FXTokenStatus status;
    private String issuerId;
    private String clientId;

    public FXTokenResponseDto(String fxTokenCode, String spTokenCode, Date contractInceptionDate, Date maturityDate, BigDecimal balance, FXTokenStatus status, String issuerId, String clientId){
        setFxTokenCode(fxTokenCode);
        setSpTokenCode(spTokenCode);
        setContractInceptionDate(contractInceptionDate);
        setMaturityDate(maturityDate);
        setBalance(balance);
        setStatus(status);
        setIssuerId(issuerId);
        setClientId(clientId);
    }

    public String getFxTokenCode() {
        return fxTokenCode;
    }

    public void setFxTokenCode(String fxTokenCode) {
        this.fxTokenCode = fxTokenCode;
    }

    public String getSpTokenCode() {
        return spTokenCode;
    }

    public void setSpTokenCode(String spTokenCode) {
        this.spTokenCode = spTokenCode;
    }

    public Date getContractInceptionDate() {
        return contractInceptionDate;
    }

    public void setContractInceptionDate(Date contractInceptionDate) {
        this.contractInceptionDate = contractInceptionDate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public FXTokenStatus getStatus() {
        return status;
    }

    public void setStatus(FXTokenStatus status) {
        this.status = status;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
