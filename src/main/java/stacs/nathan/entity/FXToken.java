package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import stacs.nathan.utils.enums.FXTokenStatus;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "fx_token")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class FXToken extends BaseTokenEntity {
    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "sp_token_id", nullable = false)
    private SPToken spToken;

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

    public SPToken getSpToken() {
        return spToken;
    }

    public void setSpToken(SPToken spToken) {
        this.spToken = spToken;
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

}
