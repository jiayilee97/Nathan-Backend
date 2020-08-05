package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Audited
@Table(name = "nav")
public class NAV extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(name = "asset_value", precision = 20, scale = 10)
    @ColumnDefault("0.0")
    private BigDecimal assetValue = BigDecimal.ZERO;

    public BigDecimal getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(BigDecimal assetValue) {
        this.assetValue = assetValue;
    }

}
