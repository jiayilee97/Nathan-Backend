package stacs.nathan.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "sp_token_id", nullable = false)
    private SPToken spToken;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    public SPToken getSpToken() {
        return spToken;
    }

    public void setSpToken(SPToken spToken) {
        this.spToken = spToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
