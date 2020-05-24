package stacs.nathan.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trade_history")
public class TradeHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private long tokenId;

    @Column
    private String type; //buy, sell

    @OneToMany(mappedBy = "tradeHistory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionHistory> transactionHistoryList;

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
