package stacs.nathan.dto.response;

import stacs.nathan.entity.FXToken;
import stacs.nathan.entity.SPToken;
import stacs.nathan.utils.enums.ProductType;
import stacs.nathan.utils.enums.SPTokenStatus;
import java.math.BigDecimal;
import java.util.Date;

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

    private String issuer; //ops display name

    private String clientId;

    private String status;

    private String issuingAddress;

    private String fxTokenCode;

    public SPTokenResponseDto(String tokenCode, String productType, Date contractInceptionDate, String underlyingCurrency, BigDecimal notionalAmount, BigDecimal amountPerFixing, BigDecimal indicativeSpotPrice, BigDecimal strikeRate, BigDecimal knockoutPrice, Date maturityDate, String fixingPage, int numFixing, String counterPartyId, String opsId, String issuingAddress, SPTokenStatus status, String issuer, String clientId, FXToken fxToken) {
        setTokenCode(tokenCode);
        setProductType(ProductType.resolveCode(productType).getValue());
        setContractInceptionDate(contractInceptionDate);
        setUnderlyingCurrency(underlyingCurrency);
        setNotionalAmount(notionalAmount);
        setAmountPerFixing(amountPerFixing);
        setIndicativeSpotPrice(indicativeSpotPrice);
        setStrikeRate(strikeRate);
        setKnockoutPrice(knockoutPrice);
        setMaturityDate(maturityDate);
        setFixingPage(fixingPage);
        setNumFixing(numFixing);
        setCounterPartyId(counterPartyId);
        setOpsId(opsId);
        setIssuingAddress(issuingAddress);
        setIssuer(issuer);
        setClientId(clientId);
        setStatus(status.getValue());
        System.out.println(tokenCode);
        if (fxToken != null) {
            setFxTokenCode(fxToken.getTokenCode());
        } else {
            setFxTokenCode(null);
        }
    }

    public SPTokenResponseDto(String tokenCode, String productType, Date contractInceptionDate, String underlyingCurrency, BigDecimal notionalAmount, BigDecimal amountPerFixing, BigDecimal indicativeSpotPrice, BigDecimal strikeRate, BigDecimal knockoutPrice, Date maturityDate, String fixingPage, int numFixing, String counterPartyId, String opsId, String issuingAddress, SPTokenStatus status, String issuer, String clientId) {
        setTokenCode(tokenCode);
        setProductType(ProductType.resolveCode(productType).getValue());
        setContractInceptionDate(contractInceptionDate);
        setUnderlyingCurrency(underlyingCurrency);
        setNotionalAmount(notionalAmount);
        setAmountPerFixing(amountPerFixing);
        setIndicativeSpotPrice(indicativeSpotPrice);
        setStrikeRate(strikeRate);
        setKnockoutPrice(knockoutPrice);
        setMaturityDate(maturityDate);
        setFixingPage(fixingPage);
        setNumFixing(numFixing);
        setCounterPartyId(counterPartyId);
        setOpsId(opsId);
        setIssuingAddress(issuingAddress);
        setIssuer(issuer);
        setClientId(clientId);
        setStatus(status.getValue());
    }

    public SPTokenResponseDto(SPToken spToken) {
        setTokenCode(spToken.getTokenCode());
        setProductType(ProductType.resolveCode(spToken.getProductType()).getValue());
        setContractInceptionDate(spToken.getContractInceptionDate());
        setUnderlyingCurrency(spToken.getUnderlyingCurrency());
        setNotionalAmount(spToken.getNotionalAmount());
        setAmountPerFixing(spToken.getFixingAmount());
        setIndicativeSpotPrice(spToken.getSpotPrice());
        setStrikeRate(spToken.getStrikeRate());
        setKnockoutPrice(spToken.getKnockOutPrice());
        setMaturityDate(spToken.getMaturityDate());
        setFixingPage(spToken.getFixingPage());
        setNumFixing(spToken.getNumberOfFixing());
        setCounterPartyId(spToken.getCpId());
        setOpsId(spToken.getOpsId());
        setIssuingAddress(spToken.getIssuingAddress());
        setIssuer(spToken.getIssuingAddress());
        setClientId(spToken.getClientId());
        setStatus(spToken.getStatus().getValue());
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

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFxTokenCode() {
        return fxTokenCode;
    }

    public void setFxTokenCode(String fxTokenCode) {
        this.fxTokenCode = fxTokenCode;
    }
}
