package stacs.nathan.entity;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class Token {

    @Column(name = "token_code", length = 50, nullable = false)
    private String tokenCode;

    @Column(name = "c_tx_id", length = 50, unique = true)
    private String ctxId;

    @Column(name = "block_height", length = 50)
    private int blockHeight;

    @Column(name = "token_contract_address", length = 50)
    private String tokenContractAddress;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
