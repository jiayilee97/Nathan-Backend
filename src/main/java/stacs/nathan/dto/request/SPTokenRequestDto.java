package stacs.nathan.dto.request;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Date;

public class SPTokenRequestDto {

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

    private Time maturityTime;

    private String fixingPage;

    private int numFixing;

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

    public Time getMaturityTime() {
        return maturityTime;
    }

    public void setMaturityTime(Time maturityTime) {
        this.maturityTime = maturityTime;
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
}
