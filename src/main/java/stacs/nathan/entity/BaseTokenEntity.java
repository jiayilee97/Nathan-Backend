package stacs.nathan.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseTokenEntity extends BaseEntity {

    @Column(name = "token_code", length = 50, nullable = false)
    private String tokenCode;

    @Column(name = "c_tx_id", length = 50, unique = true)
    private String ctxId;

    @Column(name = "block_height", length = 50)
    private String blockHeight;

    @Column(name = "token_contract_address", length = 50)
    private String tokenContractAddress;

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

}
