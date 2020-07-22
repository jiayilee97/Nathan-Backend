package stacs.nathan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import stacs.nathan.utils.enums.SPTokenStatus;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Audited
@Table(name = "sp_token")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SPToken extends BaseTokenEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "client_id", length = 50)
    private String clientId;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "fx_token_id", nullable = true)
    private FXToken fxToken;

    @Column(name = "product_type", length = 50)
    private String productType;

    @Column(name= "issuing_address", length = 50)
    private String issuingAddress;

    @Column(name = "underlying_currency", length = 10)
    private String underlyingCurrency;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "contract_inception_date")
    private Date contractInceptionDate;

    @Column(name = "notional_amount", precision = 15, scale = 2)
    @ColumnDefault("0.0")
    private BigDecimal notionalAmount = BigDecimal.ZERO;

    @Column(name = "fixing_amount", precision = 10, scale = 2)
    @ColumnDefault("0.0")
    private BigDecimal fixingAmount = BigDecimal.ZERO;

    @Column(name = "spot_price", precision = 10, scale = 5)
    @ColumnDefault("0.0")
    private BigDecimal spotPrice = BigDecimal.ZERO;

    @Column(name = "strike_rate", precision = 10, scale = 5)
    @ColumnDefault("0.0")
    private BigDecimal strikeRate = BigDecimal.ZERO;

    @Column(name = "knock_out_price", precision = 10, scale = 5)
    @ColumnDefault("0.0")
    private BigDecimal knockOutPrice = BigDecimal.ZERO;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "maturity_date")
    private Date maturityDate;

    @Column(name = "fixing_page")
    private String fixingPage;

    @Column(name = "num_fixing")
    private int numberOfFixing;

    @Column(name = "cp_id")
    private String cpId;

    @Column(name = "ops_id")
    private String opsId;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private SPTokenStatus status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public FXToken getFxToken() {
        return fxToken;
    }

    public void setFxToken(FXToken fxToken) {
        this.fxToken = fxToken;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public BigDecimal getFixingAmount() {
        return fixingAmount;
    }

    public void setFixingAmount(BigDecimal fixingAmount) {
        this.fixingAmount = fixingAmount;
    }

    public BigDecimal getSpotPrice() {
        return spotPrice;
    }

    public void setSpotPrice(BigDecimal spotPrice) {
        this.spotPrice = spotPrice;
    }

    public BigDecimal getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(BigDecimal strikeRate) {
        this.strikeRate = strikeRate;
    }

    public BigDecimal getKnockOutPrice() {
        return knockOutPrice;
    }

    public void setKnockOutPrice(BigDecimal knockOutPrice) {
        this.knockOutPrice = knockOutPrice;
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

    public int getNumberOfFixing() {
        return numberOfFixing;
    }

    public void setNumberOfFixing(int numberOfFixing) {
        this.numberOfFixing = numberOfFixing;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getOpsId() {
        return opsId;
    }

    public void setOpsId(String opsId) {
        this.opsId = opsId;
    }

    public SPTokenStatus getStatus() {
        return status;
    }

    public void setStatus(SPTokenStatus status) {
        this.status = status;
    }

    public String getIssuingAddress() {
        return issuingAddress;
    }

    public void setIssuingAddress(String issuingAddress) {
        this.issuingAddress = issuingAddress;
    }

    public Date getContractInceptionDate() {
        return contractInceptionDate;
    }

    public void setContractInceptionDate(Date contractInceptionDate) {
        this.contractInceptionDate = contractInceptionDate;
    }
}
