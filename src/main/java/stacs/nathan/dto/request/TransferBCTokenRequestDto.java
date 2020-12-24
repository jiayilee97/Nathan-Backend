package stacs.nathan.dto.request;

import java.math.BigDecimal;

public class TransferBCTokenRequestDto {
    private String bcTokenCode;
    private BigDecimal amount;
    private String investorWalletAddress;
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

    public String getInvestorWalletAddress() {
        return investorWalletAddress;
    }

    public void setInvestorWalletAddress(String investorWalletAddress) {
        this.investorWalletAddress = investorWalletAddress;
    }

    public String getFxTokenCode() {
        return fxTokenCode;
    }

    public void setFxTokenCode(String fxTokenCode) {
        this.fxTokenCode = fxTokenCode;
    }
}
