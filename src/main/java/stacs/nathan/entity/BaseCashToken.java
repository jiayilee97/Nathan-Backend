package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "base_cash_token")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BaseCashToken{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "token_id")
    private long tokenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "token_code", length = 50, nullable = false)
    private String tokenCode;

    @Column(name = "c_tx_id", length = 50, unique = true)
    private String ctxId;

    @Column(name = "block_height", length = 50)
    private int blockHeight;

    @Column(name = "token_contract_address", length = 50)
    private String tokenContractAddress;

    @Column(name = "underlying_currency", length = 10)
    private String underlyingCurrency;

    @Column(name = "currency_code", length = 10)
    private int currencyCode;

    @Column(name = "amount", precision = 15, scale = 2)
    @ColumnDefault("0.0")
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "issuer_id", length = 50)
    private String issuerId;

    @Column(name = "issuer_address", length = 50)
    private String issuerAddress;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getUnderlyingCurrency() {
        return underlyingCurrency;
    }

    public void setUnderlyingCurrency(String underlyingCurrency) {
        this.underlyingCurrency = underlyingCurrency;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseCashToken that = (BaseCashToken) o;
        return tokenId == that.tokenId &&
                blockHeight == that.blockHeight &&
                currencyCode == that.currencyCode &&
                Objects.equals(user, that.user) &&
                Objects.equals(tokenCode, that.tokenCode) &&
                Objects.equals(ctxId, that.ctxId) &&
                Objects.equals(tokenContractAddress, that.tokenContractAddress) &&
                Objects.equals(underlyingCurrency, that.underlyingCurrency) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(issuerId, that.issuerId) &&
                Objects.equals(issuerAddress, that.issuerAddress) &&
                Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenId, user, tokenCode, ctxId, blockHeight, tokenContractAddress, underlyingCurrency, currencyCode, amount, issuerId, issuerAddress, createdDate);
    }
}
