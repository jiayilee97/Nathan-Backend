package stacs.nathan.entity;

import stacs.nathan.utils.enums.UserRole;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @Column(name = "uuid", length = 50, nullable = false)
    private String uuid;

    @NotNull
    @NotEmpty(message = "Username cannot be empty")
    @Column(name = "user_name", length = 50)
    private String username;

    @NotNull
    @NotEmpty(message = "Display name cannot be empty")
    @Column(name = "display_name", length = 50)
    private String displayName;

    @Email
    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String email;

    //for client, operation, counter party, market
    @Column(name = "private_key", length = 200)
    private String privateKey;

    //for client, operation, counter party, market
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
