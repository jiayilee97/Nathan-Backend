package stacs.nathan.entity;
import org.hibernate.annotations.ColumnDefault;
import stacs.nathan.utils.enums.TokenType;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trade_history")
public class TradeHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private long tokenId;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TokenType tokenType;

    @Column
    private String side; //buy, sell

    @Column(length = 10)
    private String underlying;

    @Column(precision = 15, scale = 2)
    @ColumnDefault("0.0")
    private BigDecimal quantity = BigDecimal.ZERO;

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
