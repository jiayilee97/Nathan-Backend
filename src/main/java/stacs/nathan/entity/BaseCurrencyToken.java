package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import stacs.nathan.utils.enums.BCTokenStatus;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "base_currency_token")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BaseCurrencyToken extends BaseTokenEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private BCTokenStatus status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public BCTokenStatus getStatus() {
        return status;
    }

    public void setStatus(BCTokenStatus status) {
        this.status = status;
    }
}
