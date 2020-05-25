package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "base_currency_token")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BaseCurrencyToken extends BaseEntity {
    private static final long serialVersionUID = 1L;

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

}
