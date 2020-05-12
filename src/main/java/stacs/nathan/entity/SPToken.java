package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import stacs.nathan.Utils.enums.SPTokenStatus;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "sp_token")
public class SPToken{
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

    @Column(name = "product_type", length = 50)
    private String productType;

    @Column(name = "underlying_currency", length = 10)
    private String underlyingCurrency;

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
    @Column(length = 10)
    private SPTokenStatus status;

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
