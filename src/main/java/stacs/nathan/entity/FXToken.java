package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import stacs.nathan.enums.FXTokenStatus;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "fx_token")
public class FXToken{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "token_id")
    private long tokenId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sp_token_id", nullable = false)
    private SPToken spToken;

    @Column(name = "token_code", length = 50, nullable = false)
    private String tokenCode;

    @Column(name = "c_tx_id", length = 50, unique = true)
    private String ctxId;

    @Column(name = "block_height", length = 50)
    private int blockHeight;

    @Column(name = "token_contract_address", length = 50)
    private String tokenContractAddress;

    @Column(name = "fx_currency", length = 10)
    private String fxCurrency;

    @Column(name = "currency_code", length = 10)
    private int currencyCode;

    @Column(name = "amount", precision = 15, scale = 2)
    @ColumnDefault("0.0")
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "issuer_id", length = 50)
    private String issuerId;

    @Column(name = "issuer_address", length = 50)
    private String issuerAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private FXTokenStatus status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public SPToken getSpToken() {
        return spToken;
    }

    public void setSpToken(SPToken spToken) {
        this.spToken = spToken;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public String getCtxId() {
        return ctxId;
    }

    public void setCtxId(String ctxId) {
        this.ctxId = ctxId;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(int blockHeight) {
        this.blockHeight = blockHeight;
    }

    public String getTokenContractAddress() {
        return tokenContractAddress;
    }

    public void setTokenContractAddress(String tokenContractAddress) {
        this.tokenContractAddress = tokenContractAddress;
    }

    public String getFxCurrency() {
        return fxCurrency;
    }

    public void setFxCurrency(String fxCurrency) {
        this.fxCurrency = fxCurrency;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getIssuerAddress() {
        return issuerAddress;
    }

    public void setIssuerAddress(String issuerAddress) {
        this.issuerAddress = issuerAddress;
    }

    public FXTokenStatus getStatus() {
        return status;
    }

    public void setStatus(FXTokenStatus status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
