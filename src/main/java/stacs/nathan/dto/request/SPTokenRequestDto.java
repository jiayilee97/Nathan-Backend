package stacs.nathan.dto.request;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SPTokenRequestDto {

    private String tokenCode;

    private String productType;

    private Timestamp contractInceptionDate;

    private String underlyingCurrency;

    private BigDecimal notionalAmount;

    private BigDecimal amountPerFixing;

    private BigDecimal indicativeSpotPrice;

    private BigDecimal strikeRate;

    private BigDecimal knockoutPrice;

    private Timestamp maturityDate;

    private String fixingPage;

    private int numFixing;

    private String counterPartyId;

    private String opsId;

    private String issuingAddress;

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Timestamp getContractInceptionDate() {
        return contractInceptionDate;
    }

    public void setContractInceptionDate(Timestamp contractInceptionDate) {
        this.contractInceptionDate = contractInceptionDate;
    }

    public String getUnderlyingCurrency() {
        return underlyingCurrency;
    }

    public void setUnderlyingCurrency(String underlyingCurrency) {
        this.underlyingCurrency = underlyingCurrency;
    }

    public BigDecimal getNotionalAmount() {
        return notionalAmount;
    }

    public void setNotionalAmount(BigDecimal notionalAmount) {
        this.notionalAmount = notionalAmount;
    }

    public BigDecimal getAmountPerFixing() {
        return amountPerFixing;
    }

    public void setAmountPerFixing(BigDecimal amountPerFixing) {
        this.amountPerFixing = amountPerFixing;
    }

    public BigDecimal getIndicativeSpotPrice() {
        return indicativeSpotPrice;
    }

    public void setIndicativeSpotPrice(BigDecimal indicativeSpotPrice) {
        this.indicativeSpotPrice = indicativeSpotPrice;
    }

    public BigDecimal getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(BigDecimal strikeRate) {
        this.strikeRate = strikeRate;
    }

    public BigDecimal getKnockoutPrice() {
        return knockoutPrice;
    }

    public void setKnockoutPrice(BigDecimal knockoutPrice) {
        this.knockoutPrice = knockoutPrice;
    }

    public Timestamp getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Timestamp maturityDate) {
        this.maturityDate = maturityDate;
    }



    public String getFixingPage() {
        return fixingPage;
    }

    public void setFixingPage(String fixingPage) {
        this.fixingPage = fixingPage;
    }

    public int getNumFixing() {
        return numFixing;
    }

    public void setNumFixing(int numFixing) {
        this.numFixing = numFixing;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public String getOpsId() {
        return opsId;
    }

    public void setOpsId(String opsId) {
        this.opsId = opsId;
    }

    public String getIssuingAddress() {
        return issuingAddress;
    }

    public void setIssuingAddress(String issuingAddress) {
        this.issuingAddress = issuingAddress;
    }
}
