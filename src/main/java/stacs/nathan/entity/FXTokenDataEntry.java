package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "fx_token_data_entry")
public class FXTokenDataEntry extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "fx_token_id")
    private FXToken fxToken;

    @Column(name = "fx_currency")
    private String fxCurrency;

    @Column(name = "price", precision = 15, scale = 2)
    @ColumnDefault("0.0")
    private BigDecimal price = BigDecimal.ZERO;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "entry_date")
    private Date entryDate;

    public FXToken getFxToken() {
        return fxToken;
    }

    public void setFxToken(FXToken fxToken) {
        this.fxToken = fxToken;
    }

    public String getFxCurrency() {
        return fxCurrency;
    }

    public void setFxCurrency(String fxCurrency) {
        this.fxCurrency = fxCurrency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
}
