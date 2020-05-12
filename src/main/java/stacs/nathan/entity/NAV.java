package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import stacs.nathan.Utils.enums.NAVType;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "nav")
public class NAV {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fx_token_id", nullable = false)
    private FXToken fxToken;

    @Column(name = "sp_token_id")
    private long spTokenId;

    @Column(name = "asset_value", precision = 10, scale = 5)
    @ColumnDefault("0.0")
    private BigDecimal assetValue = BigDecimal.ZERO;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "c_tx_id", length = 50, unique = true)
    private String ctxId;

    @Column(name = "block_height", length = 50)
    private int blockHeight;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private NAVType type;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    //user_id
    @Column(name = "data_entry_address")
    private String dataEntryAddress;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FXToken getFxToken() {
        return fxToken;
    }

    public void setFxToken(FXToken fxToken) {
        this.fxToken = fxToken;
    }

    public long getSpTokenId() {
        return spTokenId;
    }

    public void setSpTokenId(long spTokenId) {
        this.spTokenId = spTokenId;
    }

    public BigDecimal getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(BigDecimal assetValue) {
        this.assetValue = assetValue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public NAVType getType() {
        return type;
    }

    public void setType(NAVType type) {
        this.type = type;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDataEntryAddress() {
        return dataEntryAddress;
    }

    public void setDataEntryAddress(String dataEntryAddress) {
        this.dataEntryAddress = dataEntryAddress;
    }
}
