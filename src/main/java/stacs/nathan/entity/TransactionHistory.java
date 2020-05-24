package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import stacs.nathan.utils.enums.TokenType;
import stacs.nathan.utils.enums.TransactionStatus;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tx_history")
public class TransactionHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id", nullable = false)
    private TradeHistory tradeHistory;

    @Column(name = "from_address", length = 100)
    private String fromAddress;

    @Column(name = "to_address", length = 100)
    private String toAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TokenType tokenType;

    @Column
    private int quantity;

    @Column(name = "balance_amount", precision = 15, scale = 2)
    @ColumnDefault("0.0")
    private BigDecimal amount;

    @Column(name = "block_height", length = 50)
    private int blockHeight;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TransactionStatus status;

    public TradeHistory getTradeHistory() {
        return tradeHistory;
    }

    public void setTradeHistory(TradeHistory tradeHistory) {
        this.tradeHistory = tradeHistory;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(int blockHeight) {
        this.blockHeight = blockHeight;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

}
