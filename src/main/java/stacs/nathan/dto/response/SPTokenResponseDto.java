package stacs.nathan.dto.response;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class SPTokenResponseDto {
    private String tokenCode;

    private String productType;

    private Date contractInceptionDate;

    private String underlyingCurrency;

    private BigDecimal notionalAmount;

    private BigDecimal amountPerFixing;

    private BigDecimal indicativeSpotPrice;

    private BigDecimal strikeRate;

    private BigDecimal knockoutPrice;

    private Date maturityDate;

    private String fixingPage;

    private int numFixing;

    private String counterPartyId;

    private String opsId;

    private String issuingAddress;

    public SPTokenResponseDto(String tokenCode, String productType, Date contractInceptionDate, String underlyingCurrency, BigDecimal notionalAmount, BigDecimal amountPerFixing, BigDecimal indicativeSpotPrice, BigDecimal strikeRate, BigDecimal knockoutPrice, Date maturityDate, String fixingPage, int numFixing, String counterPartyId, String opsId, String issuingAddress) {
        this.tokenCode = tokenCode;
        this.productType = productType;
        this.contractInceptionDate = contractInceptionDate;
        this.underlyingCurrency = underlyingCurrency;
        this.notionalAmount = notionalAmount;
        this.amountPerFixing = amountPerFixing;
        this.indicativeSpotPrice = indicativeSpotPrice;
        this.strikeRate = strikeRate;
        this.knockoutPrice = knockoutPrice;
        this.maturityDate = maturityDate;
        this.fixingPage = fixingPage;
        this.numFixing = numFixing;
        this.counterPartyId = counterPartyId;
        this.opsId = opsId;
        this.issuingAddress = issuingAddress;
    }

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

    public Date getContractInceptionDate() {
        return contractInceptionDate;
    }

    public void setContractInceptionDate(Date contractInceptionDate) {
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

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
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
