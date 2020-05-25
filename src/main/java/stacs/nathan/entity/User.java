package stacs.nathan.entity;

import stacs.nathan.utils.enums.UserRole;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(name = "private_key", length = 200)
    private String privateKey;

    @Column(name = "wallet_address", length = 50)
    private String walletAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Balance> balances;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BaseCurrencyToken> baseCurrencyTokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SPToken> spTokens;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public List<BaseCurrencyToken> getBaseCurrencyTokens() {
        return baseCurrencyTokens;
    }

    public void setBaseCurrencyTokens(List<BaseCurrencyToken> baseCurrencyTokens) {
        this.baseCurrencyTokens = baseCurrencyTokens;
    }

    public List<SPToken> getSpTokens() {
        return spTokens;
    }

    public void setSpTokens(List<SPToken> spTokens) {
        this.spTokens = spTokens;
    }
}
