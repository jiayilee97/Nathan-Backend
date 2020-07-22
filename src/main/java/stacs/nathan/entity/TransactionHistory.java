package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import stacs.nathan.utils.enums.TokenType;
import stacs.nathan.utils.enums.TransactionStatus;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Audited
@Table(name = "tx_history")
public class TransactionHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private long tokenId;

    @Column(name = "from_address", length = 100)
    private String fromAddress;

    @Column(name = "to_address", length = 100)
    private String toAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TokenType tokenType;

    @Column
    private int quantity;

    @Column(name="token_code", length = 50)
    private String tokenCode;

    @Column(name = "balance_amount", precision = 15, scale = 2)
    @ColumnDefault("0.0")
    private BigDecimal amount;

    @Column(name = "c_tx_id", length = 100, unique = true)
    private String ctxId;

    @Column(name = "block_height", length = 50)
    private String blockHeight;

    @Column(name = "token_contract_address", length = 100)
    private String tokenContractAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private TransactionStatus status;

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
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

    public String getCtxId() {
        return ctxId;
    }

    public void setCtxId(String ctxId) {
        this.ctxId = ctxId;
    }

    public String getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(String blockHeight) {
        this.blockHeight = blockHeight;
    }

    public String getTokenContractAddress() {
        return tokenContractAddress;
    }

    public void setTokenContractAddress(String tokenContractAddress) {
        this.tokenContractAddress = tokenContractAddress;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }
}
