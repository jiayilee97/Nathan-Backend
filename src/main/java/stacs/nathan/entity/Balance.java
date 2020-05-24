package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import stacs.nathan.utils.enums.TokenType;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "balance")
public class Balance extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TokenType tokenType;

    @Column(name = "balance_amount", precision = 15, scale = 2)
    @ColumnDefault("0.0")
    private BigDecimal balanceAmount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

}
